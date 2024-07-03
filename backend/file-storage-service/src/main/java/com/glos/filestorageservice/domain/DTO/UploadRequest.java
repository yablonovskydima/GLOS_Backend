package com.glos.filestorageservice.domain.DTO;

import java.util.List;

public class UploadRequest {
    private List<ByteArrayWithPath> files;

    public UploadRequest() {
    }

    public UploadRequest(List<ByteArrayWithPath> files) {
        this.files = files;
    }

    public List<ByteArrayWithPath> getFiles() {
        return files;
    }

    public void setFiles(List<ByteArrayWithPath> files) {
        this.files = files;
    }
}