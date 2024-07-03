package com.glos.api.authservice.dto;

import java.util.Map;
import java.util.Objects;

public class OperationCreateRequest {

    private String action;

    private Map<String, String> data;

    private Integer expired;

    public OperationCreateRequest() {

    }

    public OperationCreateRequest(String action, Map<String, String> data, Integer expired) {
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
