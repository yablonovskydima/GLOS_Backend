package com.glos.filestorageservice.domain.DTO;

import java.util.List;

public class DownloadRequest {

    private List<String> filenames;

    public DownloadRequest() {
    }

    public DownloadRequest(List<String> filenames) {
        this.filenames = filenames;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
