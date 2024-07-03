package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.entities.Tag;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 	@author - yablonovskydima
 */
public class FileFilter implements EntityFilter
{
    private Long id;
    private String rootPath;

    private String rootFilename;

    private String rootFullName;

    private int rootSize;

    private String rootFormat;

    private String displayPath;

    private String displayFilename;

    private String displayFullName;

    private Repository repository;

    private List<AccessType> accessTypes;

    private List<Comment> comments;

    private List<SecureCode> secureCodes;

    private List<Tag> tags;

    public FileFilter(Long id, String rootPath,
                      String rootFilename, String rootFullName,
                      int rootSize, String rootFormat, String displayPath,
                      String displayFilename, String displayFullName,
                      Repository repository, List<AccessType> accessTypes,
                      List<Comment> comments, List<SecureCode> secureCodes,
                      List<Tag> tags) {
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

    public int getRootSize() {
        return rootSize;
    }

    public void setRootSize(int rootSize) {
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
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("rootPath", rootPath);
        map.put("rootFilename", rootFilename);
        map.put("rootFullName", rootFullName);
        map.put("rootSize", rootSize);
        map.put("rootFormat", rootFormat);
        map.put("displayPath", displayPath);
        map.put("displayFilename", displayFilename);
        map.put("displayFullName", displayFullName);
        map.put("repository", repository);
        map.put("accessTypes", accessTypes);
        map.put("comments", comments);
        map.put("secureCodes", secureCodes);
        map.put("tags", tags);
        return map;
    }

    public FileFilter() {
    }
}
