package com.glos.filemanagerservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.filemanagerservice.validation.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositoryUpdateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String rootPath;

    @NotNull(message = "can't be empty")
    @NotEmpty(message = "can't be empty")
    private String rootName;
    private String description;
    private List<String> tags;

    public RepositoryUpdateDTO() {
        this.tags = new ArrayList<>();
    }

    public RepositoryUpdateDTO(String rootPath, String rootName, String description, List<String> tags) {
        this.rootPath = rootPath;
        this.rootName = rootName;
        this.description = description;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryUpdateDTO that = (RepositoryUpdateDTO) o;
        return Objects.equals(rootPath, that.rootPath) && Objects.equals(rootName, that.rootName) && Objects.equals(description, that.description) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootPath, rootName, description, tags);
    }
}
