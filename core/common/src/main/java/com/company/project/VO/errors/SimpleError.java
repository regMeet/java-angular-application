package com.company.project.VO.errors;

import lombok.Data;

@Data
public class SimpleError implements BaseError {
    private static final String ERROR_CODE = "10000";
    private String message;

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
