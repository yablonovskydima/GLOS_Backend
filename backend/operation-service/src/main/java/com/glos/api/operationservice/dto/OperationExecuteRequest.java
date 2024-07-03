package com.glos.api.operationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class OperationExecuteRequest {

    @NotNull(message = "can't be empty")
    @NotBlank(message = "can't be empty")
    @Pattern(regexp = "^[0-9A-Z]{6}$", message = "invalid code format")
    private String code;

    public OperationExecuteRequest() {
    }

    public OperationExecuteRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationExecuteRequest that = (OperationExecuteRequest) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
