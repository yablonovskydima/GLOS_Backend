package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.entities.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	@author - yablonovskydima
 */
public class UserFilter implements EntityFilter {
    private Long id;

    private String username;

    private String password_hash;

    private String email;

    private String phone_number;

    private String gender;

    private String first_name;

    private String last_name;

    private LocalDateTime birthdate;

    private boolean is_account_non_expired;

    private boolean is_account_non_locked;

    private boolean is_credentials_non_expired;

    private boolean is_enabled;

    private boolean is_deleted;

    private List<Group> groups;

    private List<Role> roles;

    private Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")));

    public UserFilter() {
    }

    public UserFilter(Long id, String username,
                      String password_hash, String email,
                      String phone_number, String gender,
                      String first_name, String last_name,
                      LocalDateTime birthdate, boolean is_account_non_expired,
                      boolean is_account_non_locked, boolean is_credentials_non_expired,
                      boolean is_enabled, boolean is_deleted,
                      List<Group> groups, List<Role> roles,
                      Pageable pageable) {
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
        this.pageable = pageable;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getGender() {
        return gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public boolean isIs_account_non_expired() {
        return is_account_non_expired;
    }

    public boolean isIs_account_non_locked() {
        return is_account_non_locked;
    }

    public boolean isIs_credentials_non_expired() {
        return is_credentials_non_expired;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Map<String, Object> asMap() {


        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("email", email);
        map.put("phone_number", phone_number);
        map.put("gender", gender);
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("birthdate", birthdate);
        map.put("is_account_non_expired", is_account_non_expired);
        map.put("is_account_non_locked", is_account_non_locked);
        map.put("is_credentials_non_expired", is_credentials_non_expired);
        map.put("is_enabled", is_enabled);
        map.put("is_deleted", is_deleted);
        map.put("groups", groups);
        map.put("roles", roles);
        map.put("pageable", pageable);
        return map;
    }
}