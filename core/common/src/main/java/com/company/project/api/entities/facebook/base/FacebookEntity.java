package com.company.project.api.entities.facebook.base;


public class FacebookEntity {
    private FacebookError error;

    public FacebookEntity() {
    }

    public FacebookError getError() {
        return error;
    }

    public void setError(FacebookError error) {
        this.error = error;
    }
}
