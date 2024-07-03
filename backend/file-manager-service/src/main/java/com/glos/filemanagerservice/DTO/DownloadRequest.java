package com.glos.filemanagerservice.DTO;

import java.util.ArrayList;
import java.util.List;

public class DownloadRequest {

    private List<String> filenames;

    public DownloadRequest() {
        this.filenames = new ArrayList<>();
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
