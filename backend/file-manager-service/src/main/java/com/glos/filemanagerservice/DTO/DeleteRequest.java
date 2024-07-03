package com.glos.filemanagerservice.DTO;

import java.util.ArrayList;
import java.util.List;

public class DeleteRequest {
    private List<String> filenames;

    public DeleteRequest() {
        this.filenames = new ArrayList<>();
    }

    public DeleteRequest(List<String> filenames) {
        this.filenames = filenames;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
