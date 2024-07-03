package com.glos.databaseAPIService.domain.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "files",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"repository_id", "root_full_name"}, name = "uq_repositories_repository_id_root_full_name")
        }
)
public class File
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "root_path", nullable = false, length = 255)
    private String rootPath;

    @Column(name = "root_filename", nullable = false, length = 255)
    private String rootFilename;

    @Column(name = "root_full_name", nullable = false, length = 255)
    private String rootFullName;

    @Column(name = "root_size", nullable = false)
    @Check(constraints = "root_size >= 0")
    private Integer rootSize;

    @Column(name = "root_format", nullable = false, length = 10)
    private String rootFormat;

    @Column(name = "display_path", length = 255)
    private String displayPath;

    @Column(name = "display_filename", length = 255)
    private String displayFilename;

    @Column(name = "display_full_name", length = 255)
    private String displayFullName;

    @Column(name = "creation_date", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "repository_id", foreignKey = @ForeignKey(name = "fk_files_repositories_id"))
    private Repository repository;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "files_access_types", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "access_type_id"),
            foreignKey = @ForeignKey(name = "fk_files_access_types_files_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_access_types_access_types_id"))
    private Set<AccessType> accessTypes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "files_comments", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"),
            foreignKey = @ForeignKey(name = "fk_files_comments_files_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_comments_comments_id"))
    private Set<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "files_secure_codes", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "secure_code_id"),
            foreignKey = @ForeignKey(name = "fk_files_secure_codes_secure_codes_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_secure_codes_files_id"))
    private Set<SecureCode> secureCodes;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "files_tags", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            foreignKey = @ForeignKey(name = "fk_files_tags_files_id"),
            inverseForeignKey = @ForeignKey(name = "fk_files_tags_tags_id")
    )
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(rootPath, file.rootPath) && Objects.equals(rootFilename, file.rootFilename) && Objects.equals(rootFullName, file.rootFullName) && Objects.equals(rootSize, file.rootSize) && Objects.equals(rootFormat, file.rootFormat) && Objects.equals(displayPath, file.displayPath) && Objects.equals(displayFilename, file.displayFilename) && Objects.equals(displayFullName, file.displayFullName) && Objects.equals(creationDate, file.creationDate) && Objects.equals(updateDate, file.updateDate) && Objects.equals(repository, file.repository) && Objects.equals(accessTypes, file.accessTypes) && Objects.equals(comments, file.comments) && Objects.equals(secureCodes, file.secureCodes) && Objects.equals(tags, file.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rootPath, rootFilename, rootFullName, rootSize, rootFormat, displayPath, displayFilename, displayFullName, creationDate, updateDate, repository, accessTypes, comments, secureCodes, tags);
    }

    @Override
    public String toString() {
        return rootFullName;
    }
}
