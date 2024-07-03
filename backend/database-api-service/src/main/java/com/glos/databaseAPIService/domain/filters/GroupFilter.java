package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entities.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

/**
 * 	@author - yablonovskydima
 */
public class GroupFilter implements EntityFilter
{
    private Long id;
    private User owner;

    private List<User> users;

    private List<AccessType> accessTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "id", id,
                "owner", owner,
                "users", users,
                "accessTypes", accessTypes
        );
    }
}
