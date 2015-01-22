package com.example.komputer_robo.cookbook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.komputer_robo.cookbook.Login.EmailAndPassword;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.SignUp.AccountUserData;
import com.example.komputer_robo.cookbook.SignUp.RestSignUpBackgroundTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    @ViewById
    EditText emailSignUp;

    @ViewById
    EditText firstnameSignUp;

    @ViewById
    EditText secondNameSignUp;

    @ViewById
    EditText passwordSignUp;


    @Bean
    @NonConfigurationInstance
    RestLoginBackgroundTask restLoginBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestSignUpBackgroundTask restSignUpBackgroundTask;


    private ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {


        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Ładowanie...");
        ringProgressDialog.setIndeterminate(true);


    }


    @Click(R.id.login)
    void login() {
        if (email.getText().length() > 0 && password.getText().length() > 0) {

            ringProgressDialog.show();

            EmailAndPassword ep = new EmailAndPassword();

            ep.email = email.getText().toString();

            ep.password = password.getText().toString();

            restLoginBackgroundTask.login(ep);


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Puste pola");

            builder.setMessage("Aby się zalogować wypełnij pola email i password");

            AlertDialog dialog = builder.create();

            dialog.show();


        }

    }

    @Click(R.id.btnSignUp)
    void signUp() {

        if (emailSignUp.getText().length() > 0 && firstnameSignUp.getText().length() > 0 && passwordSignUp.getText().length() > 0) {


            ringProgressDialog.show();

            AccountUserData userData = new AccountUserData();

            userData.email = emailSignUp.getText().toString();

            userData.firstName = firstnameSignUp.getText().toString();

            userData.lastName = secondNameSignUp.getText().toString();

            userData.password = passwordSignUp.getText().toString();


            restSignUpBackgroundTask.signUp(userData);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Puste pola");

            builder.setMessage("Aby się zarejestrować wypełnij pola email, first name i  password");

            AlertDialog dialog = builder.create();

            dialog.show();
        }


    }


    public void loginSuccess(User user) {

        ringProgressDialog.dismiss();

        Toast.makeText(this, "Witaj " + user.firstName, Toast.LENGTH_LONG).show();

        Intent intent = new Intent();

        intent.putExtra("User", user);
        setResult(0, intent);

        finish();


    }

    public void signedUpSuccess(User user) {

        ringProgressDialog.dismiss();

        Toast.makeText(this, "Witaj " + user.firstName + "! Od teraz możesz korzystać w pełni z aplikacji!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent();

        intent.putExtra("User", user);

        setResult(0, intent);

        finish();


    }

    public void showError(Exception ex) {

        ringProgressDialog.dismiss();

        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        ex.printStackTrace();

    }

    public void onBackPressed() {
        setResult(1);

        finish();


    }


}






