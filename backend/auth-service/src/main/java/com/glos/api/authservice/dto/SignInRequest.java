package com.glos.api.authservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class SignInRequest {

    @NotBlank(message = "Login can not be empty.")
    private String login;

    @NotBlank(message = "Password can not be empty.")
    private String password;

    public SignInRequest() {}

    public SignInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInRequest that = (SignInRequest) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
