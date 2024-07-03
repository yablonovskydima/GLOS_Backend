package com.glos.commentservice.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.commentservice.domain.validation.OnCreate;
import com.glos.commentservice.domain.validation.OnUpdate;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CommentDTO
{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "author is require field",
            groups = OnCreate.class)
    @Null(message = "change author is unsupported",
            groups = OnUpdate.class)
    private UserDTO author;

    @NotBlank(message = "text can't be empty",
            groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "text can't be empty",
            groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 300, message = "max count characters is 300",
            groups = {OnCreate.class, OnUpdate.class})
    private String text;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public CommentDTO(Long id, UserDTO author, String text, LocalDateTime date, LocalDateTime updateDate) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
        this.updateDate = updateDate;
    }

    public CommentDTO() {
    }
}
