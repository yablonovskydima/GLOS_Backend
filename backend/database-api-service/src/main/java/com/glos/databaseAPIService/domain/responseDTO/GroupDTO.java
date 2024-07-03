package com.glos.databaseAPIService.domain.responseDTO;


import com.glos.databaseAPIService.domain.entities.AccessType;

import java.util.*;

public class GroupDTO
{
    private Long id;

    private String name;

    private UserDTO owner;

    private Set<AccessType> accessTypes;
    private Set<UserDTO> users;


    public GroupDTO() {
        this.accessTypes = new HashSet<>();
        this.users = new HashSet<>();
    }

    public GroupDTO(Long id, String name, UserDTO owner, Set<AccessType> accessTypes, Set<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.accessTypes = accessTypes;
        this.users = users;
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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Set<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(Set<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return Objects.equals(id, groupDTO.id) && Objects.equals(name, groupDTO.name) && Objects.equals(owner, groupDTO.owner) && Objects.equals(accessTypes, groupDTO.accessTypes) && Objects.equals(users, groupDTO.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, accessTypes, users);
    }
}
