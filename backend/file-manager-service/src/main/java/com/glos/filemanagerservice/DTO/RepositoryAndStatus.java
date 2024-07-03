package com.glos.filemanagerservice.DTO;

public class RepositoryAndStatus
{
    private String filename;
    private RepositoryOperationStatus status;
    private String message;

    public RepositoryAndStatus() {
    }

    public RepositoryAndStatus(String filename, RepositoryOperationStatus status, String message) {
        this.filename = filename;
        this.status = status;
        this.message = message;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public RepositoryOperationStatus getStatus() {
        return status;
    }

    public void setStatus(RepositoryOperationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
