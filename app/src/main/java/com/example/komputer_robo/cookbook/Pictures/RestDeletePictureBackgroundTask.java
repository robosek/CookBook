package com.example.komputer_robo.cookbook.Pictures;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestDeletePictureBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void deletePicture(User user, Recipe recipe) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            restClient.deletePicture("recipeId=" + recipe.id);

            publishResult();


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {
                deletePicture(user, recipe);
            } else {
                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult() {  }






    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
