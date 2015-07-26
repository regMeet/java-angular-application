package com.company.project.api.entities.facebook.base;

import com.google.gson.annotations.SerializedName;

public class FacebookError {
    private String message;
    private String type;
    private String code;

    @SerializedName("error_subcode")
    private String subcode;

    public FacebookError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    @Override
    public String toString() {
        return "FacebookError [message=" + message + ", type=" + type + ", code=" + code + ", subcode=" + subcode + "]";
    }

}
