package com.company.project.VO.errors;

import java.util.List;

import lombok.Data;

@Data
public class ValidationErrorVO implements BaseError {
    private static final String ERROR_CODE = "11000";
    private List<ValidationErrorDataVO> data;

    public String getErrorCode() {
        return ERROR_CODE;
    }

    @Data
    public static class ValidationErrorDataVO {
        private String field;
        private String message;
    }
}
