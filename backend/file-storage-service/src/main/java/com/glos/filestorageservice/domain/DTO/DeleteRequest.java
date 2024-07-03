package com.glos.filestorageservice.domain.DTO;

import java.util.List;

public class DeleteRequest {
    private List<String> filenames;

    public DeleteRequest() {
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
