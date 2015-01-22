package com.example.komputer_robo.cookbook.ShowRecipes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.komputer_robo.cookbook.Comments.Comment;
import com.example.komputer_robo.cookbook.Comments.CommentsList;
import com.example.komputer_robo.cookbook.Comments.RestGetCommentsBackgroundTask;
import com.example.komputer_robo.cookbook.Comments.RestSendCommentBackgroundTask;
import com.example.komputer_robo.cookbook.DeleteRecipe.RestDeleteBackgroundTask;
import com.example.komputer_robo.cookbook.EditRecipe.RestEditBackgroundTask;
import com.example.komputer_robo.cookbook.Likes.Like;
import com.example.komputer_robo.cookbook.Likes.LikesList;
import com.example.komputer_robo.cookbook.Likes.RestDeleteLikeBackgroundTask;
import com.example.komputer_robo.cookbook.Likes.RestGetLikesBackgroundTask;
import com.example.komputer_robo.cookbook.Likes.RestSendLikeBackgroundTask;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.LoginActivity_;
import com.example.komputer_robo.cookbook.R;
import com.example.komputer_robo.cookbook.Recipe.Recipe;
import com.example.komputer_robo.cookbook.UserInfo.RestUserInfoBackgroundTask;
import com.example.komputer_robo.cookbook.UserInfo.UserInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@EActivity(R.layout.activity_fullrecipes)
public class FullRecipesActivity extends ActionBarActivity {

    @ViewById
    TextView tvTitle;

    @ViewById
    TextView tvIntroduction;

    @ViewById
    TextView tvIngredients;

    @ViewById
    TextView tvSteps;

    @ViewById
    TextView timeToPrepare;

    @ViewById
    TextView servings;

    @ViewById
    TextView tvOwnerId;

    @ViewById
    TextView tvHowManyLikes;

    @ViewById
    TextView tvCreatedTime;

    @ViewById
    Button btnDelete;

    @ViewById
    Button btnEdit;

    @ViewById
    TextView tvCommentsTitle;

    @ViewById
    Button btnAddComment;

    @ViewById
    Button btnLike;

    @ViewById
    ViewSwitcher vwSwitchIntroduction;

    @ViewById
    ViewSwitcher vwSwitchIngredients;

    @ViewById
    ViewSwitcher vwSwitchSteps;


    @ViewById
    EditText etIntroduction;

    @ViewById
    EditText etIngredients;

    @ViewById
    EditText etSteps;

    @ViewById
    EditText etComment;


    @ViewById
    ListView lvLista;


    @Extra("recipe")
    Recipe recipe = new Recipe();

    @Extra("User")
    User user = new User();

    @Bean
    @NonConfigurationInstance
    RestDeleteBackgroundTask restDeleteBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestEditBackgroundTask restEditBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestGetCommentsBackgroundTask restGetCommentsBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestSendCommentBackgroundTask restSendCommentBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestGetLikesBackgroundTask restGetLikesBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestSendLikeBackgroundTask restSendLikeBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestUserInfoBackgroundTask restUserInfoBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestDeleteLikeBackgroundTask restDeleteLikeBackgroundTask;


    private ProgressDialog ringProgressDialog;

    private Boolean onEdit = false;

    private ArrayList<String> comments;
    private ArrayAdapter<String> adapter;
    private List<Like> likesList;


    @AfterViews
    void init() {


        restGetLikesBackgroundTask.getLikes(recipe);

        comments = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, R.layout.row_comment, comments);

        ringProgressDialog = new ProgressDialog(this);

        ringProgressDialog.setIndeterminate(true);

        if (user.sessionId == null) {

            btnDelete.setVisibility(View.GONE);

            btnEdit.setVisibility(View.GONE);

            btnAddComment.setVisibility(View.GONE);

            etComment.setVisibility(View.GONE);

            lvLista.setVisibility(View.GONE);

            tvCreatedTime.setVisibility(View.GONE);

            tvCommentsTitle.setVisibility(View.GONE);

        } else {
            restGetCommentsBackgroundTask.getComments(user, recipe);

            restUserInfoBackgroundTask.getUserInfo(user, recipe);

            btnAddComment.setVisibility(View.VISIBLE);

            etComment.setVisibility(View.VISIBLE);

            lvLista.setVisibility(View.VISIBLE);

            tvCreatedTime.setVisibility(View.VISIBLE);

            tvCommentsTitle.setVisibility(View.VISIBLE);


            if (user.id == recipe.ownerId) {

                btnDelete.setVisibility(View.VISIBLE);

                btnEdit.setVisibility(View.VISIBLE);
            } else {
                btnDelete.setVisibility(View.GONE);

                btnEdit.setVisibility(View.GONE);
            }

        }


        if(recipe.title!=null)
        tvTitle.setText(recipe.title);
        else
            tvTitle.setText("Brak tytułu");



        if (recipe.introduction!=null && recipe.introduction.length() > 0) {
            tvIntroduction.setText(recipe.introduction);
        } else {
            tvIntroduction.setText("Brak opisu");
        }

        if (recipe.preparationMinutes!=-1) {
            timeToPrepare.setText("Czas przygotowania: " + recipe.preparationMinutes + " min.");
        } else {
            timeToPrepare.setText("Czas przygotowania: nie podano");
        }

        if (recipe.servings == 1)
            servings.setText("Dla " + recipe.servings + " osoby");
        else if (recipe.servings == 0)
            servings.setText("Nikt jej nie zje");
        else
            servings.setText("Dla " + recipe.servings + " osób");


        String[] ingredientsTab = recipe.ingredients.split(";");

        for (String s : ingredientsTab) {

            tvIngredients.setText(tvIngredients.getText() + s + " " + "\n");
        }


        if(recipe.steps!=null)
        tvSteps.setText(recipe.steps);

        if(recipe.created!=null)
        tvCreatedTime.setText(recipe.created);


    }


    @Click(R.id.btnDelete)
    void deleteRecipe() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Usunąć przepis?");

        builder.setMessage("Czy na pewno chcesz usunąc ten przepis?");

        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int witch) {

                ringProgressDialog.show();

                restDeleteBackgroundTask.deletedRecipe(user, recipe);
            }

        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int witch) {

                dialog.dismiss();
            }
        });

        ringProgressDialog.setMessage("Trwa usuwanie...");

        AlertDialog alert = builder.create();

        alert.show();


    }

    @Click(R.id.btnEdit)
    void editRecipe() {

        if (onEdit == false) {

            onEdit = true;

            btnDelete.setVisibility(View.INVISIBLE);

            btnEdit.setText("OK");

            String introduction = tvIntroduction.getText().toString();

            vwSwitchIntroduction.showNext();

            etIntroduction.setText(introduction);

            etIntroduction.setSelection(introduction.length());

            String ingredients = tvIngredients.getText().toString();

            vwSwitchIngredients.showNext();

            etIngredients.setText(ingredients);

            etIngredients.setSelection(ingredients.length());

            String steps = tvSteps.getText().toString();

            vwSwitchSteps.showNext();

            etSteps.setText(steps);

            etSteps.setSelection(steps.length());

        } else {

            onEdit = false;

            btnDelete.setVisibility(View.VISIBLE);

            btnEdit.setText("Edytuj");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Zapisać?");

            builder.setMessage("Czy chcesz zapisać zmiany ?");

            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int witch) {

                    recipe.introduction = etIntroduction.getText().toString();

                    recipe.ingredients = etIngredients.getText().toString();

                    recipe.steps = etSteps.getText().toString();

                    ringProgressDialog.setMessage("Edytuje...");

                    ringProgressDialog.show();

                    restEditBackgroundTask.deletedRecipe(user, recipe);
                }

            });
            builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int witch) {

                    vwSwitchIntroduction.showNext();

                    vwSwitchIngredients.showNext();

                    vwSwitchSteps.showNext();

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();

            alert.show();


        }


    }


    @Click(R.id.tvOwnerId)
    void goLogIn() {

        if (tvOwnerId.getText().toString().startsWith("Zaloguj")) {

            LoginActivity_.intent(this).startForResult(0);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {

            if (resultCode == 0) {

                user = (User) data.getSerializableExtra("User");

                init();

            }

        }


    }

    @Override
    public void onBackPressed() {


        if (user.id > -1) {

            Intent intent = new Intent();

            intent.putExtra("User", user);

            setResult(0, intent);
        }
        super.onBackPressed();


    }


    //Do Edita
    public void editSuccess() {
        ringProgressDialog.dismiss();

        finish();

        ShowRecipesActivity_.intent(this).extra("User", user).start();
    }

    //Do Edita
    public void publishError(Exception ex) {

        ringProgressDialog.dismiss();

        vwSwitchIntroduction.showNext();

        vwSwitchIngredients.showNext();

        vwSwitchSteps.showNext();

        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        ex.printStackTrace();


    }


    //Do deleta
    public void deleteSuccess() {

        ringProgressDialog.dismiss();

        Toast.makeText(this, "Poprawnie usunięto przepis", Toast.LENGTH_LONG).show();

        ShowRecipesActivity_.intent(this).extra("User", user).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();

        finish();


    }

    //Do deleta
    public void showError(Exception ex) {

        ringProgressDialog.dismiss();

        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();


    }


    //Do Commenta
    public void commentsDownloadedSuccess(CommentsList commentsList) {

        Iterator<Comment> iterator = commentsList.commentList.iterator();

        while (iterator.hasNext()) {

            Comment comment = iterator.next();

            comments.add(comment.text);


        }

        adapter = new ArrayAdapter<String>(this, R.layout.row_comment, comments);

        lvLista.setAdapter(adapter);


    }

    @Click(R.id.btnAddComment)
    void addComment() {

        String commentString = etComment.getText().toString();

        if (commentString.length() > 0) {

            Comment comment = new Comment();

            comment.text = commentString;

            comment.ownerId = user.id;

            comment.recipeId = recipe.id;

            restSendCommentBackgroundTask.sendComment(user, comment);

            etComment.setText("");


        }

    }


    public void sendCommentSuccess() {

        comments.clear();

        restGetCommentsBackgroundTask.getComments(user, recipe);

    }


    public void getLikesSuccess(LikesList likes) {

        likesList = likes.likes;

        int likesNumber = likes.likes.size();

        tvHowManyLikes.setText("Ilość polubień: " + likesNumber);

        Iterator<Like> iterator = likes.likes.iterator();

        Boolean liked = false;

        while (iterator.hasNext()) {

            Like like = iterator.next();

            if (like.ownerId == user.id) {
                liked = true;
                break;
            }

        }

        if (user.sessionId != null && liked == true) {

            btnLike.setText("Nie lubię");


        } else if (user.sessionId != null && liked == false) {

            btnLike.setText("Lubię to!");

        } else {

            btnLike.setVisibility(View.GONE);
        }
    }

    public void sendLikeSuccess() {

        restGetLikesBackgroundTask.getLikes(recipe);


    }

    @Click(R.id.btnLike)
    void sendLike() {
        if (btnLike.getText().toString() == "Nie lubię") {

            Iterator<Like> iterator = likesList.iterator();

            while (iterator.hasNext()) {

                Like like = iterator.next();

                if (like.ownerId == user.id) {
                    restDeleteLikeBackgroundTask.deleteLike(user, like);
                    break;
                }

            }


        } else if (btnLike.getText().toString() == "Lubię to!") {
            Like like = new Like();
            like.ownerId = user.id;
            like.recipeId = recipe.id;
            restSendLikeBackgroundTask.sendLike(user, like);
        }


    }

    public void getUserInfoSuccess(UserInfo userInfo) {


        if (user.id == userInfo.id) {
            tvOwnerId.setText("Dodany przez Ciebie");

        } else {
            if (userInfo.firstName != null)
                tvOwnerId.setText("Dodał " + userInfo.firstName);
            else
                tvOwnerId.setText("Dodał " + userInfo.email);
        }


    }

    public void deleteLikeSuccess() {

        restGetLikesBackgroundTask.getLikes(recipe);
    }


}
