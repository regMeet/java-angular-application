package com.company.project.VO;

public class ValidationErrorVO {
    private String field;
    private String message;

    public ValidationErrorVO() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
