package com.glos.databaseAPIService.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "comments",
        uniqueConstraints = @UniqueConstraint(name = "uq_comments_resource_path_author_id_text", columnNames = {"author_id", "resource_path", "text"})
)
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "resource_path", length = 100)
    private String resourcePath;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_users_id"))
    private User author;

    @Column(name = "`text`", nullable = false, length = 300)
    private String text;

    @Column(name = "creation_date", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    public Comment() {
    }

    public Comment(Long id, String resourcePath, User author, String text, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.resourcePath = resourcePath;
        this.author = author;
        this.text = text;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(resourcePath, comment.resourcePath) && Objects.equals(author, comment.author) && Objects.equals(text, comment.text) && Objects.equals(creationDate, comment.creationDate) && Objects.equals(updateDate, comment.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resourcePath, author, text, creationDate, updateDate);
    }

    @Override
    public String toString() {
        return text;
    }
}
