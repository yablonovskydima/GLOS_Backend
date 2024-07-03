package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.entities.Tag;

import java.util.*;

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

    private Set<AccessType> accessTypes;

    private Set<CommentDTO> comments;

    private Set<SecureCode> secureCodes;

    private Set<Tag> tags;

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
                         Set<AccessType> accessTypes,
                         Set<CommentDTO> comments,
                         Set<SecureCode> secureCodes,
                         Set<Tag> tags) {
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
    }

    public RepositoryDTO() {
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
        RepositoryDTO that = (RepositoryDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(rootPath, that.rootPath) && Objects.equals(rootName, that.rootName) && Objects.equals(rootFullName, that.rootFullName) && Objects.equals(owner, that.owner) && Objects.equals(isDefault, that.isDefault) && Objects.equals(displayPath, that.displayPath) && Objects.equals(displayName, that.displayName) && Objects.equals(displayFullName, that.displayFullName) && Objects.equals(description, that.description) && Objects.equals(accessTypes, that.accessTypes) && Objects.equals(comments, that.comments) && Objects.equals(secureCodes, that.secureCodes) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rootPath, rootName, rootFullName, owner, isDefault, displayPath, displayName, displayFullName, description, accessTypes, comments, secureCodes, tags);
    }
}
