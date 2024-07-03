package com.glos.filemanagerservice.DTO;

import java.util.List;

public class UpdateRequest {
    private List<FileWithPath> files;

    public UpdateRequest() {
    }

    public UpdateRequest(List<FileWithPath> filesWithPath) {
        this.files = filesWithPath;
    }

    public List<FileWithPath> getFiles() {
        return files;
    }

    public void setFiles(List<FileWithPath> filesWithPath) {
        this.files = filesWithPath;
    }
}