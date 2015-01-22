package com.example.komputer_robo.cookbook.UserInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    public int id;

    @JsonProperty("created_date")
    public String createdDate;

    @JsonProperty("last_modified_date")
    public String lastModifiedDate;

    @JsonProperty("display_name")
    public String displayName;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    public String email;



}
