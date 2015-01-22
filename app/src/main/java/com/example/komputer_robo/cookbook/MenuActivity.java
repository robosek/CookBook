package com.example.komputer_robo.cookbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.ShowRecipesActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_menu)
public class MenuActivity extends ActionBarActivity {


    @ViewById
    TextView tvlogin;


    @Extra("User")
    User user = new User();


    private Intent intent;

    @AfterViews
    void init() {

        intent = new Intent(this, LoginActivity_.class);
        if (user.firstName != null) {

            tvlogin.setText("Witaj " + user.firstName);
        }

    }

    @Click({R.id.share})
    void openFirstStep() {

        if (user.sessionId != null) {

            FirstStepActivity_.intent(this).extra("User", user).start();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Brak autoryzacji");
            builder.setMessage("Aby dodać własny przepis musisz być zalogowany. Czy chcesz się zalogować ?");
            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int witch) {

                    startActivityForResult(intent, 0);


                }

            });
            builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int witch) {

                    dialog.dismiss();

                }

            });

            AlertDialog dialog = builder.create();
            dialog.show();


        }

    }


    @Click(R.id.find)
    void openRecipes() {

        ShowRecipesActivity_.intent(this).extra("User", user).startForResult(0);

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz opuścić aplikację?");
        builder.setTitle("Zakończyć działanie?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int witch) {
                finish();
                System.exit(0);

            }

        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int witch) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Click(R.id.btnlogin)
    void goToLogin() {

        if (user.sessionId == null) {
            LoginActivity_.intent(this).startForResult(0);

        } else {
            Toast.makeText(this, "Jesteś już zalogowany " + user.firstName, Toast.LENGTH_LONG).show();
        }
    }


    @Click(R.id.help)
    void showSomeMessage(){
        Toast.makeText(this,"Aby uzyskać pomoc napisz maila robert@wp.pl, chętnie pomogę!",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {

            if (resultCode == 0) {

                user = (User) data.getSerializableExtra("User");

                init();

            }

        }


    }


}
