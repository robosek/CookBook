package com.example.komputer_robo.cookbook.UserInfo;

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
public class RestUserInfoBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getUserInfo(User user, Recipe recipe) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);
            UserInfo userInfo = restClient.getUserInfo(recipe.ownerId);
            publishResult(userInfo);


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {
                getUserInfo(user, recipe);
            } else {
                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult(UserInfo userInfo) {

        activity.getUserInfoSuccess(userInfo);


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
