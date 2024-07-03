package com.glos.api.operationservice.dto;

import java.util.Objects;

public class ChangeRequest {
    private String oldValue;
    private String newValue;

    public ChangeRequest() {
    }

    public ChangeRequest(String oldValue, String newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public boolean oldEqualNew() {
        return Objects.equals(oldValue, newValue);
    }

}