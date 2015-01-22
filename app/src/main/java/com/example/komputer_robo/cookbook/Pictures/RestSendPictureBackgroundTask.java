package com.example.komputer_robo.cookbook.Pictures;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ThirdStepActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestSendPictureBackgroundTask {

    private final static int maxRepeat = 3;

    private int i = 0;

    @RootContext
    ThirdStepActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void sendPicture(User user, Picture picture) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            restClient.sendPicture(picture);

            publishResult();


        } catch (Exception ex) {
            i++;
            if (i <= maxRepeat) {
                sendPicture(user, picture);
            } else {
                publishError(ex);
            }


        }

    }

    @UiThread
    void publishResult() {

        activity.sendPictureSuccess();


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}
