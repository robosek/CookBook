package com.example.komputer_robo.cookbook.SendRecipe;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.Recipe.Recipe;
import com.example.komputer_robo.cookbook.ThirdStepActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;



@EBean
public class RestSendBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    ThirdStepActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void sendRecipe(User user, Recipe recipe) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            Recipe recipeId = restClient.sendRecipe(recipe);

            publishResult(recipeId.id);


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {

                sendRecipe(user, recipe);

            } else {

                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult(int id) {

        activity.sendSuccess(id);


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
