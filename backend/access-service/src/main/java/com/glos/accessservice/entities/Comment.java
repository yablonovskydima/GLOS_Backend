package com.glos.accessservice.entities;

import java.time.LocalDateTime;
import java.util.Objects;


public class Comment
{
    private Long id;

    private User author;

    private String text;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

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
        return Objects.equals(id, comment.id) && Objects.equals(author, comment.author) && Objects.equals(text, comment.text) && Objects.equals(creationDate, comment.creationDate) && Objects.equals(updateDate, comment.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, creationDate, updateDate);
    }

    @Override
    public String toString() {
        return text;
    }
}
