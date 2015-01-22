package com.example.komputer_robo.cookbook.Comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komputer - Robo on 2015-01-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsList  {

    @JsonProperty("record")
   public List<Comment> commentList = new ArrayList<Comment>();



}
