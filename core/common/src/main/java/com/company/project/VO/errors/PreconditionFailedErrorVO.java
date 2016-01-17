package com.company.project.VO.errors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreconditionFailedErrorVO implements BaseError {
    private static final String ERROR_CODE = "12000";
    private String message;
    private List<String> fields;

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
