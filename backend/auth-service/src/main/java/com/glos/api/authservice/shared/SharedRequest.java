package com.glos.api.authservice.shared;

public class SharedRequest {

    private Long expired;
    private String rootFullName;

    public SharedRequest() {
    }

    public SharedRequest(Long expired, String rootFullName) {
        this.expired = expired;
        this.rootFullName = rootFullName;
    }

    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    public String getRootFullName() {
        return rootFullName;
    }

    public void setRootFullName(String rootFullName) {
        this.rootFullName = rootFullName;
    }
}
