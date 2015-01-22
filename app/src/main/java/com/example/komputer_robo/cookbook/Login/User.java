package com.example.komputer_robo.cookbook.Login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public int id;

    @JsonProperty("session_id")
    public String sessionId;

    @JsonProperty("first_name")
    public String firstName;






}



