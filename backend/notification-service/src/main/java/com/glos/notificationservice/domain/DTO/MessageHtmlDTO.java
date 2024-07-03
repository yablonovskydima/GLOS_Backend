package com.glos.notificationservice.domain.DTO;

import com.glos.notificationservice.domain.entity.Content;

import java.util.Map;

public class MessageHtmlDTO {

    private String to;
    private String subject;
    private String plainHtml;
    private Map<String, String> data;

    public MessageHtmlDTO() {
    }

    public MessageHtmlDTO(String to, String subject, String plainHtml, Map<String, String> data) {
        this.to = to;
        this.subject = subject;
        this.plainHtml = plainHtml;
        this.data = data;
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

    public String getPlainHtml() {
        return plainHtml;
    }

    public void setPlainHtml(String plainHtml) {
        this.plainHtml = plainHtml;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
