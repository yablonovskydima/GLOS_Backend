package com.glos.api.authservice.util.security;

import jakarta.validation.constraints.NotBlank;

public class JwtRequest {

    @NotBlank(message = "Username can not be empty.")
    private String username;

    @NotBlank(message = "Password can not be empty.")
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
