package com.glos.accessservice.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;


import java.util.ArrayList;
import java.util.List;

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

    private Repository repository;

    private List<AccessType> accessTypes;

    private List<Comment> comments;


    private List<SecureCode> secureCodes;

    private List<Tag> tags;

    public File() {
        this.repository = new Repository();
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.secureCodes = new ArrayList<>();
        this.accessTypes = new ArrayList<>();
    }

    public File(Long id, String rootPath, String rootFilename, String rootFullName, Integer rootSize, String rootFormat, String displayPath, String displayFilename, String displayFullName, Repository repository, List<AccessType> accessTypes, List<Comment> comments, List<SecureCode> secureCodes, List<Tag> tags) {
        this.id = id;
        this.rootPath = rootPath;
        this.rootFilename = rootFilename;
        this.rootFullName = rootFullName;
        this.rootSize = rootSize;
        this.rootFormat = rootFormat;
        this.displayPath = displayPath;
        this.displayFilename = displayFilename;
        this.displayFullName = displayFullName;
        this.repository = repository;
        this.accessTypes = accessTypes;
        this.comments = comments;
        this.secureCodes = secureCodes;
        this.tags = tags;
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
