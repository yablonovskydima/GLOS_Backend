package com.glos.filemanagerservice.DTO;

public class FileAndStatus {

    private String filename;
    private FileOperationStatus status;
    private String message;

    public FileAndStatus() {
    }

    public FileAndStatus(String filename, FileOperationStatus status, String message) {
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

    public FileOperationStatus getStatus() {
        return status;
    }

    public void setStatus(FileOperationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
