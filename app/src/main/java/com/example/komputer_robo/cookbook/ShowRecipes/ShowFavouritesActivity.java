package com.example.komputer_robo.cookbook.ShowRecipes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.example.komputer_robo.cookbook.FavouriteRecipes.RestGetFavouritesBackgroundTask;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.MenuActivity_;
import com.example.komputer_robo.cookbook.R;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_favouirtes)
@OptionsMenu(R.menu.menu)
public class ShowFavouritesActivity extends ActionBarActivity {

    @ViewById
    ListView lvListaFavourites;

    @Bean
    RecipeFavouriteListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestGetFavouritesBackgroundTask restGetFavouritesBackgroundTask;

    @Extra("User")
    User user;

    private ProgressDialog ringProgressDialog;


    private List<Recipe> listFavourites;


    @AfterViews
    void init() {

        listFavourites = new ArrayList<Recipe>();

        adapter.setList(listFavourites);

        lvListaFavourites.setAdapter(adapter);

        ringProgressDialog = new ProgressDialog(this);

        ringProgressDialog.setMessage("Ładuję...");

        ringProgressDialog.setIndeterminate(true);

        ringProgressDialog.show();

        restGetFavouritesBackgroundTask.getFavourites(user);


    }

    public void onBackPressed() {

        setResult(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        finish();
    }



    @Click(R.id.btnBack)
    void backToMenu() {

        onBackPressed();

        MenuActivity_.intent(this).extra("User", user).start();


    }


    @ItemClick(R.id.lvListaFavourites)
    void itemClicked(Recipe recipe) {

        FullRecipesActivity_.intent(this).extra("recipe", recipe).extra("User", user).start();
    }


    public void showError(Exception ex) {

        ringProgressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(ex.getMessage());

        AlertDialog alert = builder.create();

        alert.show();


        ex.printStackTrace();

    }


    public void updateCookbook(Recipe recipe) {

        ringProgressDialog.dismiss();

        adapter.updateCookbook(recipe);

    }


}









































