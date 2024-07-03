package com.glos.filestorageservice.domain.DTO;

public class UserBucketAndStatus
{
    private String username;
    private BucketOperationStatus status;
    private String message;

    public UserBucketAndStatus() {
    }

    public UserBucketAndStatus(String username, BucketOperationStatus status, String message) {
        this.username = username;
        this.status = status;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BucketOperationStatus getStatus() {
        return status;
    }

    public void setStatus(BucketOperationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
