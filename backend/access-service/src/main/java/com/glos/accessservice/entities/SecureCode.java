package com.glos.accessservice.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;



public class SecureCode
{
    private Long id;

    private String code;

    private String resourcePath;

    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;

    public SecureCode(Long id, String code, String resourcePath, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.id = id;
        this.code = code;
        this.resourcePath = resourcePath;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public SecureCode() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }


    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SecureCode that = (SecureCode) object;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(resourcePath, that.resourcePath) && Objects.equals(creationDate, that.creationDate) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, resourcePath, creationDate, expirationDate);
    }
}
