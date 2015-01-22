package com.example.komputer_robo.cookbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.listview)
public class RecipeView extends RelativeLayout {


    @ViewById
    ImageView pictureMeal;

    @ViewById
    TextView title;


    @ViewById
    TextView ingredients;


    public RecipeView(Context context) {
        super(context);
    }


    public void recipeBind(Recipe recipe) {


        if (recipe.pictureBytes != null) {

            decodeAndSetImage(recipe.pictureBytes, pictureMeal);
        } else {

            pictureMeal.setImageResource(R.drawable.talerz);
        }

        title.setText(recipe.title);

        ingredients.setText("Składniki: " + recipe.ingredients);




    }

    private void decodeAndSetImage(String base64bytes, ImageView image) {

        byte[] decodedString = Base64.decode(base64bytes, Base64.DEFAULT);

        Bitmap decodedBytes = BitmapFactory.decodeByteArray(decodedString, 0,

                decodedString.length);

        image.setImageBitmap(decodedBytes);

    }


}







