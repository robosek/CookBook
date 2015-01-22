package com.example.komputer_robo.cookbook.Pictures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Picture {

    public Integer id;

    public Integer ownerId;

    public String base64bytes;

    public Integer recipeId;



}
