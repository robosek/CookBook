package com.example.komputer_robo.cookbook.Comments;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Komputer - Robo on 2015-01-13.
 */

@EBean
public class RestSendCommentBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void sendComment(User user, Comment comment) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            restClient.sendComment(comment);

            publishResult();


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {
                sendComment(user, comment);
            } else {
                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult() {

        activity.sendCommentSuccess();


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
