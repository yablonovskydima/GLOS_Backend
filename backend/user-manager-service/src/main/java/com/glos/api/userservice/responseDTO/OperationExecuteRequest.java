package com.glos.api.userservice.responseDTO;


import java.util.Objects;

public class OperationExecuteRequest {

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
