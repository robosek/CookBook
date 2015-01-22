package com.example.komputer_robo.cookbook;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.example.komputer_robo.cookbook.Login.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_secondstep)
public class SecondStepActivity extends ActionBarActivity {

    @ViewById
    EditText etIntroduction;

    @ViewById
    EditText etSteps;


    //Oderbanie extras z poprzedniej aktywności
    @Extra("Name")
    String name;

    @Extra("Ingredients")
    String ingredients;

    @Extra("User")
    User user;


    @AfterViews
    void init() {

        etSteps.setSelection(0);

    }

    @Click({R.id.btnNextStep})
    void goToNextStep() {

        if (etSteps.length() > 0) {

            finish();
            ThirdStepActivity_.intent(this)
                    .extra("Name", name)
                    .extra("Ingredients", ingredients)
                    .extra("Introduction", etIntroduction.getText().toString())
                    .extra("Steps", etSteps.getText().toString())
                    .extra("User", user).start();


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nie wszystkie pola wypełnione");
            builder.setMessage("Należy dodać przynajmniej kroki przygotowania potrawy");
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }

}

























