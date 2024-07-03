package com.glos.api.userservice.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.api.userservice.entities.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO
{
    private Long id;

    private String username;

    private String email;

    private String phoneNumber;

    private String gender;

    private String firstName;

    private String lastName;

    private LocalDateTime birthdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean blocked;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deleted;

    private List<Role> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deletedDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime blockedDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime disabledDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedDataTime;

    public UserDTO() {
        this.roles = new ArrayList<>();
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getDeletedDateTime() {
        return deletedDateTime;
    }

    public void setDeletedDateTime(LocalDateTime deletedDateTime) {
        this.deletedDateTime = deletedDateTime;
    }

    public LocalDateTime getBlockedDateTime() {
        return blockedDateTime;
    }

    public void setBlockedDateTime(LocalDateTime blockedDateTime) {
        this.blockedDateTime = blockedDateTime;
    }

    public LocalDateTime getDisabledDateTime() {
        return disabledDateTime;
    }

    public void setDisabledDateTime(LocalDateTime disabledDateTime) {
        this.disabledDateTime = disabledDateTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDataTime() {
        return updatedDataTime;
    }

    public void setUpdatedDataTime(LocalDateTime updatedDataTime) {
        this.updatedDataTime = updatedDataTime;
    }
}
