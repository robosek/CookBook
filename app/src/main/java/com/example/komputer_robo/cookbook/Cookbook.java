package com.example.komputer_robo.cookbook;

import com.example.komputer_robo.cookbook.Recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Cookbook {

    @JsonProperty("record")
    public List<Recipe> records = new ArrayList<Recipe>();



}
