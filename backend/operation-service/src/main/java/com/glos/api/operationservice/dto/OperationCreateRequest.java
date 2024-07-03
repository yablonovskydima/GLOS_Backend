package com.glos.api.operationservice.dto;

import com.glos.api.operationservice.dto.validation.annotation.ValidAction;
import com.glos.api.operationservice.dto.validation.annotation.ValidOperationData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import com.glos.api.operationservice.Operations;

import java.util.Map;
import java.util.Objects;

@ValidOperationData(
        message = "Invalid data properties for specified action",
        ignoreProperties = {"code"})
public class OperationCreateRequest {

    @ValidAction
    @NotBlank(message = "can't be empty")
    private String action;

    @NotNull(message = "can't be null")
    private Map<String, String> data;

    @Range(min = 1, max = 3600, message = "value is out of range 1 to 2000")
    private Integer expired = Operations.DEFAULT_EXPIRED_SECONDS;

    public OperationCreateRequest() {

    }

    public OperationCreateRequest(String action, Map<String, String> data, int expired) {
        this.action = action;
        this.data = data;
        this.expired = expired;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationCreateRequest that = (OperationCreateRequest) o;
        return expired == that.expired && Objects.equals(action, that.action) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, data, expired);
    }
}
