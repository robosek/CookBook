package com.example.komputer_robo.cookbook.ShowRecipes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.MenuActivity_;
import com.example.komputer_robo.cookbook.R;
import com.example.komputer_robo.cookbook.RecipeListAdapter;
import com.example.komputer_robo.cookbook.RestBackgroundTask;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_showrecipes)
@OptionsMenu(R.menu.menu_activity_recipes)
public class ShowRecipesActivity extends ActionBarActivity {


    @ViewById
    ListView lvLista;

    @OptionsMenuItem
    MenuItem action_favourite;

    @Bean
    RecipeListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestBackgroundTask restBackgroundTask;

    @Extra("User")
    User user;


    private ProgressDialog ringProgressDialog;


    private List<Recipe> lista;


    @AfterViews
    void init() {




        lista = new ArrayList<Recipe>();

        adapter.setList(lista);

        lvLista.setAdapter(adapter);

        ringProgressDialog = new ProgressDialog(this);

        ringProgressDialog.setMessage("Ładuję...");

        ringProgressDialog.setIndeterminate(true);

        ringProgressDialog.show();


        restBackgroundTask.getCookbook();


    }




    @OptionsItem(R.id.action_favourite)
    void goToFavourite() {

        if (user.id > 0) {

            ShowFavouritesActivity_.intent(this).extra("User", user).start();

        } else {

            Toast.makeText(this, "Musisz być zalogowany aby przeglądać ulubione przepisy", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onBackPressed() {

        if (user.id > -1) {

            Intent intent = new Intent();

            intent.putExtra("User", user);

            setResult(0, intent);
        }
        super.onBackPressed();


    }


    @Click(R.id.btnBack)
    void backToMenu() {

        onBackPressed();

        MenuActivity_.intent(this).extra("User", user).start();


    }

    @ItemClick(R.id.lvLista)
    void itemClicked(Recipe recipe) {



           FullRecipesActivity_.intent(this).extra("recipe", recipe).extra("User", user).startForResult(0);



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {

            if (resultCode == 0) {

                user = (User) data.getSerializableExtra("User");

            }
        }


    }


}









































