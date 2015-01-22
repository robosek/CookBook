package com.example.komputer_robo.cookbook.Comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment  {


    public Integer id;

    public Integer recipeId;

    public Integer ownerId;

    public String text;

    public String created;

    @JsonIgnore
    public String firstName;



}
