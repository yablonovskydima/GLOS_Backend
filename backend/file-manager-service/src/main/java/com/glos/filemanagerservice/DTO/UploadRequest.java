package com.glos.filemanagerservice.DTO;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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