package com.company.project.api.entities.facebook;

import com.company.project.api.entities.facebook.base.FacebookEntity;
import com.google.gson.annotations.SerializedName;

public class FacebookUser extends FacebookEntity {
    private Long id;
    private String name;
    @SerializedName("first_name")
    private String firstname;
    @SerializedName("last_name")
    private String lastname;

    public FacebookUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
