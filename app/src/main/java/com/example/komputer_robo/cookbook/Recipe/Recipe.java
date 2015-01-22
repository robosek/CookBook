package com.example.komputer_robo.cookbook.Recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe implements Serializable, Comparable<Recipe> {

    public int id;

    public int ownerId;

    public String title;

    public String ingredients;

    public String introduction;

    public String steps;

    public String created;

    public int preparationMinutes;

    public int cookingMinutes;

    public int servings;

    @JsonIgnore
    public String pictureBytes;


    @Override
    public int compareTo(Recipe recipe) {

        if (id > recipe.id) {
            return -1;
        } else if (id == recipe.id) {
            return 0;

        } else {
            return 1;
        }


    }
}

























