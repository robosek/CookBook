package com.example.komputer_robo.cookbook;

import com.example.komputer_robo.cookbook.Login.EmailAndPassword;
import com.example.komputer_robo.cookbook.Login.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestLoginBackgroundTask {

    private static final int maxRepeat = 3;

    private int i = 0;

    @RootContext
    LoginActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void login(EmailAndPassword emailAndPassword) {


        try {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            User user = restClient.login(emailAndPassword);
            publishResult(user);


        } catch (Exception ex) {

            i++;

            if (i <= maxRepeat) {
                login(emailAndPassword);
            } else {

                publishError(ex);
            }
        }

    }

    @UiThread
    void publishResult(User user) {

        activity.loginSuccess(user);


    }

    @UiThread
    void publishError(Exception ex) {
        activity.showError(ex);
    }


}
