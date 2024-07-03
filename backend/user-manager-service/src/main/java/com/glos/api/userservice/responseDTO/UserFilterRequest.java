package com.glos.api.userservice.responseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserFilterRequest {
    private Long id;

    private String username;
    private String email;

    private String phoneNumber;

    private String gender;

    private String firstName;

    private String lastName;

    private LocalDateTime birthdate;

    private Boolean blocked;

    private Boolean enabled;

    private Boolean deleted;
    private List<String> roles;
    private List<String> groups;

    private int page;
    private int size;
    private String sort;
    private String login;

    public UserFilterRequest() {
        this.roles = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public UserFilterRequest(Long id, String username, String email, String login, String phoneNumber, String gender, String firstName, String lastName, LocalDateTime birthdate, Boolean blocked, Boolean enabled, Boolean deleted, List<String> roles, List<String> groups, int page, int size, String sort) {
        this.id = id;
        this.username = username;;
        this.email = email;
        this.login = login;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.blocked = blocked;
        this.enabled = enabled;
        this.deleted = deleted;
        this.roles = roles;
        this.groups = groups;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
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
