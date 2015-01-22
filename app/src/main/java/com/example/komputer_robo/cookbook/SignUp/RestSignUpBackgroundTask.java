package com.example.komputer_robo.cookbook.SignUp;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.LoginActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestSignUpBackgroundTask {

    private static final int maxRepeat = 3;
    private int i = 0;
    @RootContext
    LoginActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void signUp(AccountUserData userData) {


        try {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            User user = restClient.signUp(userData);
            publishResult(user);


        } catch (Exception ex) {

            i++;

            if (i <= maxRepeat) {
                signUp(userData);
            } else {

                publishError(ex);
            }
        }

    }

    @UiThread
    void publishResult(User user) {

        activity.signedUpSuccess(user);


    }

    @UiThread
    void publishError(Exception ex) {
        activity.showError(ex);
    }


}
