package com.example.komputer_robo.cookbook.Likes;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestGetLikesBackgroundTask {

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getLikes(Recipe recipe) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            LikesList likes = restClient.getLikes("recipeId=" + recipe.id);

            publishResult(likes);


        } catch (Exception ex) {

            publishError(ex);
        }

    }

    @UiThread
    void publishResult(LikesList likes) {

        activity.getLikesSuccess(likes);


    }

    @UiThread
    void publishError(Exception ex) {

        activity.publishError(ex);
    }


}
