package com.example.komputer_robo.cookbook.Likes;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestSendLikeBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void sendLike(User user, Like like) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            restClient.sendLike(like);

            publishResult();


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {
                sendLike(user, like);
            } else {
                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult() {

        activity.sendLikeSuccess();


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
