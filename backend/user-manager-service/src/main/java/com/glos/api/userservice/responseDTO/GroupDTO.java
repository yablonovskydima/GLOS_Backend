package com.glos.api.userservice.responseDTO;

import java.util.List;

public class GroupDTO
{
    private Long id;
    private String name;
    private UserDTO owner;
    private List<UserDTO> users;

    public GroupDTO(Long id, String name, UserDTO owner, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
    }

    public GroupDTO() {
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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
