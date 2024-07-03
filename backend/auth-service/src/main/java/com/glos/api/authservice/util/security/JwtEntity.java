package com.glos.api.authservice.util.security;

import com.glos.api.authservice.dto.Role;
import com.glos.api.authservice.entities.Roles;
import com.glos.api.authservice.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JwtEntity implements UserDetails {

    private User user;
    private Supplier<User> userSupplier;

    public JwtEntity() {
    }

    public JwtEntity(Supplier<User> userSupplier) {
        this.userSupplier = userSupplier;
    }

    public User getUser() {
        return ensureUser();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return ensureUser().getUsername();
    }

    public void setUsername(String username) {
        ensureUser().setUsername(username);
    }

    @Override
    public String getPassword() {
        return ensureUser().getPassword_hash();
    }

    public void setPassword(String passwordHash) {
        ensureUser().setPassword_hash(passwordHash);
    }

    public String getEmail() {
        return ensureUser().getEmail();
    }

    public void setEmail(String email) {
        ensureUser().setEmail(email);
    }

    public String getPhoneNumber() {
        return ensureUser().getPhone_number();
    }

    public void setPhoneNumber(String phoneNumber) {
        ensureUser().setPhone_number(phoneNumber);
    }

    public String getGender() {
        return ensureUser().getGender();
    }

    public void setGender(String gender) {
        ensureUser().setGender(gender);
    }

    public String getFirstName() {
        return ensureUser().getFirst_name();
    }

    public void setFirstName(String firstName) {
        ensureUser().setFirst_name(firstName);
    }

    public String getLastName() {
        return ensureUser().getLast_name();
    }

    public void setLastName(String lastName) {
        ensureUser().setLast_name(lastName);
    }

    public LocalDate getBirthdate() {
        return ensureUser().getBirthdate();
    }

    public void setBirthdate(LocalDate birthdate) {
        ensureUser().setBirthdate(birthdate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ensureUser().getRoles().stream().map( x -> new Role(x.getName())).collect(Collectors.toSet());
    }

    public void setAuthorities(Set<Roles> roles) {
        ensureUser().setRoles(roles.stream().map(Roles::asEntity).toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return ensureUser().getIs_account_non_expired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return ensureUser().getIs_account_non_locked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ensureUser().getIs_credentials_non_expired();
    }

    @Override
    public boolean isEnabled() {
        return ensureUser().getIs_enabled();
    }

    public Boolean getIs_account_non_expired() {
        return ensureUser().getIs_account_non_expired();
    }

    public void setIs_account_non_expired(Boolean is_account_non_expired) {
        ensureUser().setIs_account_non_expired(is_account_non_expired);
    }

    public void setIs_account_non_locked(Boolean is_account_non_locked) {
        ensureUser().setIs_account_non_locked(is_account_non_locked);
    }

    public void setIs_credentials_non_expired(Boolean is_credentials_non_expired) {
        ensureUser().setIs_credentials_non_expired(is_credentials_non_expired);
    }

    public void setIs_enabled(Boolean is_enabled) {
        ensureUser().setIs_enabled(is_enabled);
    }

    public Boolean getIs_deleted() {
        return ensureUser().getIs_deleted();
    }

    public void setIs_deleted(Boolean is_deleted) {
        ensureUser().setIs_deleted(is_deleted);
    }

    private User ensureUser() {
        if (user == null) user = userSupplier.get();
        return user;
    }
}
