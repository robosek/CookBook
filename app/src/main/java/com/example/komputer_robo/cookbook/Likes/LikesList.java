package com.example.komputer_robo.cookbook.Likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komputer - Robo on 2015-01-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikesList{

    @JsonProperty("record")
    public List<Like> likes = new ArrayList<Like>();

}
