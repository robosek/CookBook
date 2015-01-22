package com.example.komputer_robo.cookbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komputer_robo.cookbook.Pictures.Picture;
import com.example.komputer_robo.cookbook.Pictures.RestSendPictureBackgroundTask;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.SendRecipe.RestSendBackgroundTask;
import com.example.komputer_robo.cookbook.ShowRecipes.ShowRecipesActivity_;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


@EActivity(R.layout.activity_thirdstep)
public class ThirdStepActivity extends ActionBarActivity {

    private static final int SELECT_FILE = 2;

    @ViewById
    ImageView pictureView;

    @ViewById
    EditText etPreparationMinutes;

    @ViewById
    EditText etCookingMinutes;

    @ViewById
    EditText etServings;


    //Oderbanie extras z poprzedniej aktywności
    @Extra("Name")
    String name;

    @Extra("Ingredients")
    String ingredients;

    @Extra("Introduction")
    String introduction;

    @Extra("Steps")
    String steps;

    @Extra("User")
    User user = new User();

    private Uri fileUri;

    private String base64Image;

    @Bean
    @NonConfigurationInstance
    RestSendBackgroundTask restSendBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestSendPictureBackgroundTask restSendPictureBackgroundTask;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);

        ringProgressDialog.setMessage("Wysyłam...");

        ringProgressDialog.setIndeterminate(true);


    }

    @Click({R.id.btnPicture})
    void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //1 = MEDIA_TYPE_IMAGE
        fileUri = getOutputMediaFileUri(1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //100 = CAPTURE_IMAGE_COD
        startActivityForResult(intent, 100);


    }


    @OnActivityResult(100)
    void onResult(int result, Intent intent) {

        if (result != 0)
            setImageToPictureView(fileUri.getPath());


    }

    //Zmniejsza, koduje na base64 i ustawia w pictureVie bitmapę
    void setImageToPictureView(String fileUriPath) {

        Bitmap bitmap = resizeBitmap(100, 100, fileUriPath);

        base64Image = decodeImageToString(bitmap);

        pictureView.setImageBitmap(bitmap);

    }

    @Click({R.id.btnFinish})
    void sendToServer() {


        int preparingMinutes = 0;

        int cookingMinutes = 0;

        int servings = 0;

        try {
            if (etPreparationMinutes.getText().toString().trim().length() > 0)

                preparingMinutes = new Integer(etPreparationMinutes.getText().toString());

            if (etCookingMinutes.getText().toString().trim().length() > 0)

                cookingMinutes = new Integer(etCookingMinutes.getText().toString());

            if (etServings.getText().toString().trim().length() > 0) {

                servings = new Integer(etServings.getText().toString());

                Recipe recipe = new Recipe();

                recipe.title = name;

                recipe.introduction = introduction;

                recipe.steps = steps;

                recipe.servings = servings;

                recipe.ingredients = ingredients;

                recipe.cookingMinutes = cookingMinutes;

                recipe.preparationMinutes = preparingMinutes;

                recipe.ownerId = user.id;


                ringProgressDialog.show();
                restSendBackgroundTask.sendRecipe(user, recipe);


            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Ile osób zje tą potrawę?");
                builder.setTitle("Brak ilości osób");
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        } catch (Exception ex) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("W polach tekstowych wymagany jest format liczbowy, bez liter np.20");
            builder.setTitle("Błędny format");
            AlertDialog dialog = builder.create();
            dialog.show();


        }


    }

    //Po wysłaniu przepisu, dosyła jeszcze zdjęcie, jeśli jest przypisane
    public void sendSuccess(int id) {
        if (base64Image != null) {

            Picture picture = new Picture();

            picture.base64bytes = base64Image;

            picture.ownerId = user.id;

            picture.recipeId = id;

            restSendPictureBackgroundTask.sendPicture(user, picture);


        } else {
            sendPictureSuccess();

        }


    }

    public void sendPictureSuccess() {

        ringProgressDialog.dismiss();

        Toast.makeText(this, "Poprawnie dodano przepis", Toast.LENGTH_LONG).show();

        setResult(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        finish();

        ShowRecipesActivity_.intent(this).extra("User", user).start();


    }

    public void showError(Exception ex) {

        ringProgressDialog.dismiss();

        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        ex.printStackTrace();
    }


    //Wycągnięcie URI z pliku
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    //Tworzenie zdjęcia i zapisanie go na karcie pamięci
    private static File getOutputMediaFile(int type) {


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyCameraApp");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if (type == 1) {


                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "IMG_" + timeStamp + ".jpg");

            } else if (type == 2) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_" + timeStamp + ".mp4");
            } else {
                return null;
            }


            return mediaFile;
        } else {


            return null;
        }

    }

    //Pomniejsza obraz do 100px na 100px
    public Bitmap resizeBitmap(int targetW, int targetH, String photoPath) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;

        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {

            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;

        bmOptions.inSampleSize = scaleFactor;

        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    //Bitmap to String
    public String decodeImageToString(Bitmap bitmap) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);

        byte[] imageBytes = output.toByteArray();

        String encodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedString;
    }


    @Click(R.id.btnPictureFromGallery)
    void getPictureFromGallery() {


        startSelectFromGalleryIntent();

    }

    //Przechodzi do galerii
    private void startSelectFromGalleryIntent() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    //Powrót z galerii
    @OnActivityResult(SELECT_FILE)
    void onPhotoFromGallerySelected(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();

            String selectedImageFilePath = getPath(selectedImageUri, this);

            setImageToPictureView(selectedImageFilePath);


        }
    }


    public String getPath(Uri uri, Activity activity) {

        String[] projection = {MediaStore.MediaColumns.DATA};

        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }


}
