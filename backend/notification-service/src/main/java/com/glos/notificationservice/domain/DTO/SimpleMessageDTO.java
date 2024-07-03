package com.glos.notificationservice.domain.DTO;

import com.glos.notificationservice.domain.entity.Content;

import java.util.Map;

public class SimpleMessageDTO {

    private String to;
    private String subject;
    private String text;

    public SimpleMessageDTO() {
    }

    public SimpleMessageDTO(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
