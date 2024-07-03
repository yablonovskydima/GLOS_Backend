package com.glos.accessservice.responseDTO;

public class SharedTokenResponse
{
    private String token;

    public SharedTokenResponse(String token) {
        this.token = token;
    }

    public SharedTokenResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
