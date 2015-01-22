package com.example.komputer_robo.cookbook.Login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAndPassword {

    public String email;

    public String password;


}
