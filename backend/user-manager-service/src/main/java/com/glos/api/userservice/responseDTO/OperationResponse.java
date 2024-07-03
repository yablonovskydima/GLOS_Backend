package com.glos.api.userservice.responseDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class OperationResponse {
    private String code;
    private String action;
    private Map<String, String> data;
    private LocalDateTime createdDatetime;
    private LocalDateTime expiredDatetime;

    public OperationResponse() {
    }

    public OperationResponse(String code, String action, Map<String, String> data, LocalDateTime createdDatetime, LocalDateTime expiredDatetime) {
        this.code = code;
        this.action = action;
        this.data = data;
        this.createdDatetime = createdDatetime;
        this.expiredDatetime = expiredDatetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public LocalDateTime getExpiredDatetime() {
        return expiredDatetime;
    }

    public void setExpiredDatetime(LocalDateTime expiredDatetime) {
        this.expiredDatetime = expiredDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationResponse that = (OperationResponse) o;
        return Objects.equals(code, that.code) && Objects.equals(action, that.action) && Objects.equals(data, that.data) && Objects.equals(createdDatetime, that.createdDatetime) && Objects.equals(expiredDatetime, that.expiredDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, action, data, createdDatetime, expiredDatetime);
    }
}
