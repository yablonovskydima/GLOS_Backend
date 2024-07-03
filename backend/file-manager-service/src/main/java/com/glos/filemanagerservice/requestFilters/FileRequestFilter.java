package com.glos.filemanagerservice.requestFilters;

import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.entities.SecureCode;
import com.glos.filemanagerservice.entities.Tag;

import java.util.List;

public class FileRequestFilter
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

    private List<AccessType> accessTypes;

    private List<CommentDTO> comments;
    private List<SecureCode> secureCodes;
    private List<Tag> tags;

    private int page;
    private int size;
    private String sort;

    public FileRequestFilter(Long id, String rootPath, String rootFilename, String rootFullName, Integer rootSize, String rootFormat, String displayPath, String displayFilename, String displayFullName, RepositoryDTO repository, List<AccessType> accessTypes, List<CommentDTO> comments, List<SecureCode> secureCodes, List<Tag> tags, int page, int size, String sort) {
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
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public FileRequestFilter()
    {
        this.repository = new RepositoryDTO();
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
