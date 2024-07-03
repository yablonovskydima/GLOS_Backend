package com.glos.accessservice.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Repository
{

    private Long id;

    private String rootPath;

    private String rootName;

    private String rootFullName;

    private User owner;

    private Boolean isDefault;

    private String displayPath;

    private String displayName;

    private String displayFullName;

    private String description;

    private List<AccessType> accessTypes;
    private List<Comment> comments;
    private List<SecureCode> secureCodes;
    private List<Tag> tags;

    private List<File> files;

    public Repository() {
        this.comments = new ArrayList<>();
        this.files = new ArrayList<>();
        this.secureCodes = new ArrayList<>();
        this.accessTypes = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public Repository(Long id, String rootPath, String rootName, String rootFullName, User owner, Boolean isDefault, String displayPath, String displayName, String displayFullName, String description, List<AccessType> accessTypes, List<Comment> comments, List<SecureCode> secureCodes, List<Tag> tags, List<File> files) {
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
        this.accessTypes = accessTypes;
        this.comments = comments;
        this.secureCodes = secureCodes;
        this.tags = tags;
        this.files = files;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<SecureCode> getSecureCodes() {
        return secureCodes;
    }

    public void setSecureCodes(List<SecureCode> secureCodes) {
        this.secureCodes = secureCodes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return Objects.equals(id, that.id) && Objects.equals(rootPath, that.rootPath) && Objects.equals(rootName, that.rootName) && Objects.equals(rootFullName, that.rootFullName) && Objects.equals(owner, that.owner) && Objects.equals(isDefault, that.isDefault) && Objects.equals(displayPath, that.displayPath) && Objects.equals(displayName, that.displayName) && Objects.equals(displayFullName, that.displayFullName) && Objects.equals(description, that.description) && Objects.equals(accessTypes, that.accessTypes) && Objects.equals(comments, that.comments) && Objects.equals(secureCodes, that.secureCodes) && Objects.equals(tags, that.tags) && Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rootPath, rootName, rootFullName, owner, isDefault, displayPath, displayName, displayFullName, description, accessTypes, comments, secureCodes, tags, files);
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", rootPath='" + rootPath + '\'' +
                ", rootName='" + rootName + '\'' +
                ", rootFullName='" + rootFullName + '\'' +
                ", owner=" + owner +
                ", isDefault=" + isDefault +
                ", displayPath='" + displayPath + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayFullName='" + displayFullName + '\'' +
                ", description='" + description + '\'' +
                ", accessTypes=" + accessTypes +
                ", comments=" + comments +
                ", secureCodes=" + secureCodes +
                ", tags=" + tags +
                ", files=" + files +
                '}';
    }


}

