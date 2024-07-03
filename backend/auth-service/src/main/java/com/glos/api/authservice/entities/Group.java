package com.glos.api.authservice.entities;

import java.util.ArrayList;
import java.util.List;

public class Group
{
    private Long id;
    private String name;
    private User owner;
    private List<User> users;
    private List<AccessType> accessTypes;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public Group()
    {
        this.users = new ArrayList<>();
        this.accessTypes = new ArrayList<>();
    }

    public Group(Long id,
                 String name,
                 User owner,
                 List<User> users,
                 List<AccessType> accessTypes) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
        this.accessTypes = accessTypes;
    }
}