package com.glos.filestorageservice.domain.DTO;

import java.util.List;

public class ExistsRequest {

    private List<String> rootFullNames;

    public ExistsRequest(List<String> rootFullNames) {
        this.rootFullNames = rootFullNames;
    }

    public List<String> getRootFullNames() {
        return rootFullNames;
    }

    public void setRootFullNames(List<String> rootFullNames) {
        this.rootFullNames = rootFullNames;
    }
}
