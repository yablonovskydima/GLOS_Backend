package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.SecureCode;
import com.glos.filemanagerservice.entities.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDTO
{
    private Long id;

    private String rootPath;

    private String rootName;

    private String rootFullName;

    private UserDTO owner;

    private Boolean isDefault;

    private String displayPath;

    private String displayName;

    private String displayFullName;

    private String description;

    private List<CommentDTO> comments;

    private List<String> tags;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public RepositoryDTO(Long id,
                         String rootPath,
                         String rootName,
                         String rootFullName,
                         UserDTO owner,
                         Boolean isDefault,
                         String displayPath,
                         String displayName,
                         String displayFullName,
                         String description,
                         List<CommentDTO> comments,
                         List<String> tags,
                         LocalDateTime creationDate,
                         LocalDateTime updateDate) {
        this.id = id;
        this.rootPath = rootPath;
        this.rootName = rootName;
        this.rootFullName = rootFullName;
        this.owner = owner;
        this.isDefault = isDefault;
        this.displayPath = displayPath;
        this.displayName = displayName;
        this.displayFullName = displayFullName;
        this.description = description;
        this.comments = comments;
        this.tags = tags;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public RepositoryDTO() {
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRootFullName() {
        return rootFullName;
    }

    public void setRootFullName(String rootFullName) {
        this.rootFullName = rootFullName;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFullName() {
        return displayFullName;
    }

    public void setDisplayFullName(String displayFullName) {
        this.displayFullName = displayFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
