package com.glos.filemanagerservice.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class File
{
    private Long id;
    private String rootPath;
    private String rootFilename;
    private String rootFullName;
    private Integer rootSize;
    private String rootFormat;
    private String displayPath;
    private String displayFilename;
    private String displayFullName;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private Repository repository;
    private Set<AccessType> accessTypes;
    private Set<Comment> comments;
    private Set<SecureCode> secureCodes;
    private Set<Tag> tags;

    public File() {
        this.repository = new Repository();
        this.tags = new HashSet<>();
        this.comments = new HashSet<>();
        this.secureCodes = new HashSet<>();
        this.accessTypes = new HashSet<>();
    }

    public File(Long id, String rootPath, String rootFilename, String rootFullName, Integer rootSize, String rootFormat, String displayPath, String displayFilename, String displayFullName, LocalDateTime creationDate, LocalDateTime updateDate, Repository repository, Set<AccessType> accessTypes, Set<Comment> comments, Set<SecureCode> secureCodes, Set<Tag> tags) {
        this.id = id;
        this.rootPath = rootPath;
        this.rootFilename = rootFilename;
        this.rootFullName = rootFullName;
        this.rootSize = rootSize;
        this.rootFormat = rootFormat;
        this.displayPath = displayPath;
        this.displayFilename = displayFilename;
        this.displayFullName = displayFullName;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.repository = repository;
        this.accessTypes = accessTypes;
        this.comments = comments;
        this.secureCodes = secureCodes;
        this.tags = tags;
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

    public String getRootFilename() {
        return rootFilename;
    }

    public void setRootFilename(String rootFilename) {
        this.rootFilename = rootFilename;
    }

    public String getRootFullName() {
        return rootFullName;
    }

    public void setRootFullName(String rootFullName) {
        this.rootFullName = rootFullName;
    }

    public Integer getRootSize() {
        return rootSize;
    }

    public void setRootSize(Integer rootSize) {
        this.rootSize = rootSize;
    }

    public String getRootFormat() {
        return rootFormat;
    }

    public void setRootFormat(String rootFormat) {
        this.rootFormat = rootFormat;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public String getDisplayFilename() {
        return displayFilename;
    }

    public void setDisplayFilename(String displayFilename) {
        this.displayFilename = displayFilename;
    }

    public String getDisplayFullName() {
        return displayFullName;
    }

    public void setDisplayFullName(String displayFullName) {
        this.displayFullName = displayFullName;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Set<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(Set<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<SecureCode> getSecureCodes() {
        return secureCodes;
    }

    public void setSecureCodes(Set<SecureCode> secureCodes) {
        this.secureCodes = secureCodes;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", rootPath='" + rootPath + '\'' +
                ", rootFilename='" + rootFilename + '\'' +
                ", rootFullName='" + rootFullName + '\'' +
                ", rootSize=" + rootSize +
                ", rootFormat='" + rootFormat + '\'' +
                ", displayPath='" + displayPath + '\'' +
                ", displayFilename='" + displayFilename + '\'' +
                ", displayFullName='" + displayFullName + '\'' +
                ", repository=" + repository +
                ", accessTypes=" + accessTypes +
                ", comments=" + comments +
                ", secureCodes=" + secureCodes +
                ", tags=" + tags +
                '}';
    }
}
