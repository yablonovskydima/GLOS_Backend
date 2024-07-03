package com.glos.api.userservice.responseDTO;

import com.glos.api.userservice.entities.AccessType;
import com.glos.api.userservice.entities.User;

import java.util.ArrayList;
import java.util.List;

public class GroupFilterRequest {

    private Long id;
    private String name;
    private User owner;
    private List<User> users;
    private List<AccessType> accessTypes;
    private int page;
    private int size;
    private String sort;


    public GroupFilterRequest() {
        this.users = new ArrayList<>();
        this.accessTypes = new ArrayList<>();
    }

    public GroupFilterRequest(Long id, String name, User owner, List<User> users, List<AccessType> accessTypes, int page, int size, String sort) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
        this.accessTypes = accessTypes;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
