package com.glos.accessservice.entities;
public class AccessType
{
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public AccessType() {
    }

    public AccessType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
