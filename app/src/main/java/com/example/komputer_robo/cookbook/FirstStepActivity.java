package com.example.komputer_robo.cookbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



import com.example.komputer_robo.cookbook.Login.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_firststep)
public class FirstStepActivity extends ActionBarActivity {

    @ViewById
    EditText etName;

    @ViewById
     EditText etIngredient;

    @ViewById
     ListView lvLista;

    @ViewById
    Button btnNextStep;

    @Extra("User")
    User user;

    //Deklaracja listy składników i adaptera
    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    @AfterViews
    void init(){
    ingredients = new ArrayList<String>();
    adapter = new ArrayAdapter<String>(this,R.layout.row,ingredients);


}
    @ItemLongClick({R.id.lvLista})
    void deleteItem(final int position){



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć składnik?");
        builder.setMessage("Czy na pewno chcesz usunąc ten składnik?");

        builder.setPositiveButton("Tak",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog,int witch){
                ingredients.remove(position);
                lvLista.setAdapter(adapter);
            }

        });
        builder.setNegativeButton("Nie",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int witch){
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();

        alert.show();

    }

    @Click({R.id.btnAdd})
    void addItemToList(){


        String ingredient = etIngredient.getText().toString();
        if(ingredient.trim().length()!=0){


            ingredients.add(ingredient);
            adapter = new ArrayAdapter<String>(this,R.layout.row,ingredients);

            lvLista.setAdapter(adapter);
            etIngredient.setText("");



        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Brak składnika");
            builder.setMessage("Musisz wpisać jaki składnik checsz dodać");
            AlertDialog dialog = builder.create();
            dialog.show();


        }

    }

    @Click({R.id.btnNextStep})
    void goToNextStep(){

        String name = etName.getText().toString();

        if(name.trim().length() > 0 && !ingredients.isEmpty()){

            String ingredientsString ="";

            for(String s : ingredients){

                ingredientsString+=s+";";
            }

            finish();
            SecondStepActivity_.intent(this).extra("Name",name).extra("Ingredients",ingredientsString).extra("User",user).start();

        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nie wszystkie pola wypełnione");
            builder.setMessage("Należy podać nazwę posiłku i składniki niezbędne do jego ugutowania");
            AlertDialog dialog = builder.create();
            dialog.show();


        }

    }





}






























