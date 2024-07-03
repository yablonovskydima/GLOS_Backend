package com.glos.filemanagerservice.requestFilters;

import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.UserDTO;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.entities.SecureCode;
import com.glos.filemanagerservice.entities.Tag;

import java.util.ArrayList;
import java.util.List;

public class RepositoryRequestFilter
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

    private List<AccessType> accessTypes;

    private List<CommentDTO> comments;

    private List<SecureCode> secureCodes;

    private List<Tag> tags;

    private int page;
    private int size;
    private String sort;

    public RepositoryRequestFilter(Long id, String rootPath, String rootName, String rootFullName, UserDTO owner, Boolean isDefault, String displayPath, String displayName, String displayFullName, String description, List<AccessType> accessTypes, List<CommentDTO> comments, List<SecureCode> secureCodes, List<Tag> tags, int page, int size, String sort) {
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
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public RepositoryRequestFilter()
    {
        this.accessTypes = new ArrayList<>();
        this.secureCodes = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.owner = new UserDTO();
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

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
