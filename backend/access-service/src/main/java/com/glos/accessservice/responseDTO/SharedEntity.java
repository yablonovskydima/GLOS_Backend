package com.glos.accessservice.responseDTO;

public class SharedEntity {

    private Long expired;


    private String rootFullName;

    public SharedEntity(Long expired,  String rootFullName) {
        this.expired = expired;
        this.rootFullName = rootFullName;
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

}