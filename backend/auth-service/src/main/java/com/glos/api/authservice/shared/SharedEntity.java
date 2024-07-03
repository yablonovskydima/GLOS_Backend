package com.glos.api.authservice.shared;

public class SharedEntity {

    private Long expired;

    private String rootFullName;
    private String code;

    public SharedEntity(Long expired, String rootFullName, String code) {
        this.expired = expired;
        this.rootFullName = rootFullName;
        this.code = code;
    }

    public SharedEntity() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
