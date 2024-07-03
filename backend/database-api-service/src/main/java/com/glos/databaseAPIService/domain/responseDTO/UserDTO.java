package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.databaseAPIService.domain.entities.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserDTO
{
    private Long id;

    private String username;

    private String password_hash;

    private String email;

    private String phone_number;

    private String gender;

    private String first_name;

    private String last_name;

    private LocalDateTime birthdate;

    private Boolean is_account_non_expired;

    private Boolean is_account_non_locked;

    private Boolean is_credentials_non_expired;

    private Boolean is_enabled;

    private Boolean is_deleted;

    private Set<Role> roles;

    private LocalDateTime deletedDateTime;

    private LocalDateTime blockedDateTime;

    private LocalDateTime disabledDateTime;

    private LocalDateTime createdDateTime;

    private LocalDateTime updatedDataTime;

    public UserDTO(Long id, String username, String password_hash, String email, String phone_number, String gender, String first_name, String last_name, LocalDateTime birthdate, Boolean is_account_non_expired, Boolean is_account_non_locked, Boolean is_credentials_non_expired, Boolean is_enabled, Boolean is_deleted, Set<Role> roles, LocalDateTime deletedDateTime, LocalDateTime blockedDateTime, LocalDateTime disabledDateTime, LocalDateTime createdDateTime, LocalDateTime updatedDataTime) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.is_account_non_expired = is_account_non_expired;
        this.is_account_non_locked = is_account_non_locked;
        this.is_credentials_non_expired = is_credentials_non_expired;
        this.is_enabled = is_enabled;
        this.is_deleted = is_deleted;
        this.roles = roles;
        this.deletedDateTime = deletedDateTime;
        this.blockedDateTime = blockedDateTime;
        this.disabledDateTime = disabledDateTime;
        this.createdDateTime = createdDateTime;
        this.updatedDataTime = updatedDataTime;
    }

    public UserDTO() {
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

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getIs_account_non_expired() {
        return is_account_non_expired;
    }

    public void setIs_account_non_expired(Boolean is_account_non_expired) {
        this.is_account_non_expired = is_account_non_expired;
    }

    public Boolean getIs_account_non_locked() {
        return is_account_non_locked;
    }

    public void setIs_account_non_locked(Boolean is_account_non_locked) {
        this.is_account_non_locked = is_account_non_locked;
    }

    public Boolean getIs_credentials_non_expired() {
        return is_credentials_non_expired;
    }

    public void setIs_credentials_non_expired(Boolean is_credentials_non_expired) {
        this.is_credentials_non_expired = is_credentials_non_expired;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(username, userDTO.username) && Objects.equals(password_hash, userDTO.password_hash) && Objects.equals(email, userDTO.email) && Objects.equals(phone_number, userDTO.phone_number) && Objects.equals(gender, userDTO.gender) && Objects.equals(first_name, userDTO.first_name) && Objects.equals(last_name, userDTO.last_name) && Objects.equals(birthdate, userDTO.birthdate) && Objects.equals(is_account_non_expired, userDTO.is_account_non_expired) && Objects.equals(is_account_non_locked, userDTO.is_account_non_locked) && Objects.equals(is_credentials_non_expired, userDTO.is_credentials_non_expired) && Objects.equals(is_enabled, userDTO.is_enabled) && Objects.equals(is_deleted, userDTO.is_deleted) && Objects.equals(roles, userDTO.roles) && Objects.equals(deletedDateTime, userDTO.deletedDateTime) && Objects.equals(blockedDateTime, userDTO.blockedDateTime) && Objects.equals(disabledDateTime, userDTO.disabledDateTime) && Objects.equals(createdDateTime, userDTO.createdDateTime) && Objects.equals(updatedDataTime, userDTO.updatedDataTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password_hash, email, phone_number, gender, first_name, last_name, birthdate, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, is_deleted, roles, deletedDateTime, blockedDateTime, disabledDateTime, createdDateTime, updatedDataTime);
    }
}
