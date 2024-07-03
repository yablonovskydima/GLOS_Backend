package com.glos.filemanagerservice.DTO;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class FileRequestDTO {

    @NotEmpty(message = "can't be empty")
    private String rootPath;

    private String rootFilename;

    private String rootFormat;

    private RepositoryDTO repository;

    private List<AccessModel> accessModels;
    private List<String> tags;

    public FileRequestDTO() {
    }

    public FileRequestDTO(String rootPath, String rootFilename, String rootFormat, RepositoryDTO repository, List<AccessModel> accessModels, List<String> tags) {
        this.rootPath = rootPath;
        this.rootFilename = rootFilename;
        this.rootFormat = rootFormat;
        this.repository = repository;
        this.accessModels = accessModels;
        this.tags = tags;
    }

    public String getRootPath() {

        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootFilename() {
        return rootFilename;
    }

    public void setRootFilename(String rootFilename) {
        this.rootFilename = rootFilename;
    }

    public String getRootFormat() {
        return rootFormat;
    }

    public void setRootFormat(String rootFormat) {
        this.rootFormat = rootFormat;
    }

    public RepositoryDTO getRepository() {
        return repository;
    }

    public void setRepository(RepositoryDTO repository) {
        this.repository = repository;
    }

    public List<AccessModel> getAccessModels() {
        return accessModels;
    }

    public void setAccessModels(List<AccessModel> accessModels) {
        this.accessModels = accessModels;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
