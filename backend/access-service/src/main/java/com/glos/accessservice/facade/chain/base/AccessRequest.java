package com.glos.accessservice.facade.chain.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccessRequest {

    private String path;
    private String username;
    private boolean readOnly;
    private Map<String, Object> data;

    public AccessRequest() {
        data = new HashMap<>();
    }

    public AccessRequest(String path, String username, boolean readOnly, Map<String, Object> data) {
        this.path = path;
        this.username = username;
        this.readOnly = readOnly;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

