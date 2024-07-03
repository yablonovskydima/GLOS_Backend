package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.entities.Tag;

import java.util.*;

public class FileDTO
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

    private RepositoryDTO repository;

    private Set<AccessType> accessTypes;

    private Set<CommentDTO> comments;
    private Set<SecureCode> secureCodes;
    private Set<Tag> tags;

    public FileDTO(Long id,
                   String rootPath,
                   String rootFilename,
                   String rootFullName,
                   Integer rootSize,
                   String rootFormat,
                   String displayPath,
                   String displayFilename,
                   String displayFullName,
                   RepositoryDTO repository,
                   Set<AccessType> accessTypes,
                   Set<CommentDTO> comments,
                   Set<SecureCode> secureCodes,
                   Set<Tag> tags) {
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

    public FileDTO() {
        this.accessTypes = new HashSet<>();
        this.comments = new HashSet<>();
        this.secureCodes = new HashSet<>();
        this.tags = new HashSet<>();
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

    public RepositoryDTO getRepository() {
        return repository;
    }

    public void setRepository(RepositoryDTO repository) {
        this.repository = repository;
    }

    public Set<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(Set<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Objects.equals(id, fileDTO.id) && Objects.equals(rootPath, fileDTO.rootPath) && Objects.equals(rootFilename, fileDTO.rootFilename) && Objects.equals(rootFullName, fileDTO.rootFullName) && Objects.equals(rootSize, fileDTO.rootSize) && Objects.equals(rootFormat, fileDTO.rootFormat) && Objects.equals(displayPath, fileDTO.displayPath) && Objects.equals(displayFilename, fileDTO.displayFilename) && Objects.equals(displayFullName, fileDTO.displayFullName) && Objects.equals(repository, fileDTO.repository) && Objects.equals(accessTypes, fileDTO.accessTypes) && Objects.equals(comments, fileDTO.comments) && Objects.equals(secureCodes, fileDTO.secureCodes) && Objects.equals(tags, fileDTO.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rootPath, rootFilename, rootFullName, rootSize, rootFormat, displayPath, displayFilename, displayFullName, repository, accessTypes, comments, secureCodes, tags);
    }
}
