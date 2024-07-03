package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.entities.Tag;
import jakarta.persistence.AccessType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	@author - yablonovskydima
 */

public class RepositoryFilter implements EntityFilter
{
    private Long id;

    private String rootPath;

    private String rootName;

    private String rootFullName;

    private Long ownerId;
    private String ownerUsername;

    private boolean isDefault;

    private String displayPath;

    private String displayName;

    private String displayFullName;

    private String description;

    private List<AccessType> accessTypes;

    private List<Comment> comments;

    private List<SecureCode> secureCodes;

    private List<Tag> tags;

    private List<File> files;

    public RepositoryFilter() {}

    public RepositoryFilter(Long id, String rootPath,
                            String rootName, String rootFullName,
                            Long ownerId, String ownerUsername, boolean isDefault, String displayPath,
                            String displayName, String displayFullName,
                            String description, List<AccessType> accessTypes,
                            List<Comment> comments, List<SecureCode> secureCodes,
                            List<Tag> tags, List<File> files) {
        this.id = id;
        this.rootPath = rootPath;
        this.rootName = rootName;
        this.rootFullName = rootFullName;
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
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


    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
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
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("rootPath", rootPath);
        map.put("rootName", rootName);
        map.put("rootFullName", rootFullName);
        map.put("ownerId", ownerId);
        map.put("ownerUsername", ownerUsername);
        map.put("isDefault", isDefault);
        map.put("displayPath", displayPath);
        map.put("displayName", displayName);
        map.put("displayFullName", displayFullName);
        map.put("description", description);
        map.put("accessTypes", accessTypes);
        map.put("comments", comments);
        map.put("secureCodes", secureCodes);
        map.put("tags", tags);
        map.put("files", files);
        return map;
    }
}
