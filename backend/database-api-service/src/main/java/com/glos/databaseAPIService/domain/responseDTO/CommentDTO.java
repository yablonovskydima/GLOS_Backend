package com.glos.databaseAPIService.domain.responseDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDTO
{
    private Long id;

    private String resourcePath;

    private UserDTO author;

    private String text;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String resourcePath, UserDTO author, String text, LocalDateTime creationDate, LocalDateTime updateDate) {
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

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
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
        CommentDTO that = (CommentDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(resourcePath, that.resourcePath) && Objects.equals(author, that.author) && Objects.equals(text, that.text) && Objects.equals(creationDate, that.creationDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resourcePath, author, text, creationDate, updateDate);
    }
}
