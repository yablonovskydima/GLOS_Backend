package com.glos.api.authservice.dto;

public class SharedTokenResponse
{
    private String token;
    private String sharedUrl;

    public SharedTokenResponse() {
    }

    public SharedTokenResponse(String token, String sharedUrl) {
        this.token = token;
        this.sharedUrl = sharedUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }
}
