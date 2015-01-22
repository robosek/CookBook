package com.example.komputer_robo.cookbook.Likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {

    public int id;

    public int ownerId;

    public int recipeId;

    public String created;




}

