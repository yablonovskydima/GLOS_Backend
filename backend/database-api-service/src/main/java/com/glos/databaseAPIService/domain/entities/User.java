package com.glos.databaseAPIService.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username", name = "uq_users_username"),
                @UniqueConstraint(columnNames = "email", name = "uq_users_email"),
                @UniqueConstraint(columnNames = "phone_number", name = "uq_users_phone_number")
        }
)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "username", nullable = false, length = 40, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 72)
    private String password_hash;

    @Column(name = "email", nullable = false, length = 320, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15, unique = true)
    private String phone_number;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "first_name", length = 50)
    private String first_name;

    @Column(name = "last_name", length = 50)
    private String last_name;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "is_account_non_expired", nullable = false, columnDefinition="boolean default true")
    private Boolean is_account_non_expired;

    @Column(name = "is_account_non_locked", nullable = false, columnDefinition = "boolean default true")
    private Boolean is_account_non_locked;

    @Column(name = "is_credentials_non_expired", nullable = false, columnDefinition = "boolean default true")
    private Boolean is_credentials_non_expired;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default true")
    private Boolean is_enabled;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean is_deleted;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Group> groups;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_users_id"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_roles_id"))
    private Set<Role> roles;

    @Column(name = "deleted_datetime")
    private LocalDateTime deletedDateTime;

    @Column(name = "blocked_datetime")
    private LocalDateTime blockedDateTime;

    @Column(name = "disabled_datetime")
    private LocalDateTime disabledDateTime;

    @Column(name = "created_datetime", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime", columnDefinition = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDataTime;

    public User() {
        this.roles = new HashSet<>();
        this.groups = new HashSet<>();
    }

    public User(Long id, String username, String password_hash, String email, String phone_number, String gender, String first_name, String last_name, LocalDate birthdate, Boolean is_account_non_expired, Boolean is_account_non_locked, Boolean is_credentials_non_expired, Boolean is_enabled, Boolean is_deleted, Set<Group> groups, Set<Role> roles, LocalDateTime deletedDateTime, LocalDateTime blockedDateTime, LocalDateTime disabledDateTime, LocalDateTime createdDateTime, LocalDateTime updatedDataTime) {
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
        this.groups = groups;
        this.roles = roles;
        this.deletedDateTime = deletedDateTime;
        this.blockedDateTime = blockedDateTime;
        this.disabledDateTime = disabledDateTime;
        this.createdDateTime = createdDateTime;
        this.updatedDataTime = updatedDataTime;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
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

    public void addGroup(Group group)
    {
        this.groups.add(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password_hash, user.password_hash) && Objects.equals(email, user.email) && Objects.equals(phone_number, user.phone_number) && Objects.equals(gender, user.gender) && Objects.equals(first_name, user.first_name) && Objects.equals(last_name, user.last_name) && Objects.equals(birthdate, user.birthdate) && Objects.equals(is_account_non_expired, user.is_account_non_expired) && Objects.equals(is_account_non_locked, user.is_account_non_locked) && Objects.equals(is_credentials_non_expired, user.is_credentials_non_expired) && Objects.equals(is_enabled, user.is_enabled) && Objects.equals(is_deleted, user.is_deleted) && Objects.equals(groups, user.groups) && Objects.equals(roles, user.roles) && Objects.equals(deletedDateTime, user.deletedDateTime) && Objects.equals(blockedDateTime, user.blockedDateTime) && Objects.equals(disabledDateTime, user.disabledDateTime) && Objects.equals(createdDateTime, user.createdDateTime) && Objects.equals(updatedDataTime, user.updatedDataTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password_hash, email, phone_number, gender, first_name, last_name, birthdate, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, is_deleted, groups, roles, deletedDateTime, blockedDateTime, disabledDateTime, createdDateTime, updatedDataTime);
    }

    @Override
    public String toString() {
        return username;
    }
}
