package com.glos.api.userservice.responseDTO;

import java.util.Map;

public class MessageVerificationDTO
{
    private String to;
    private String subject;
    private String text;
    private Map<String, String> data;

    public MessageVerificationDTO(){}

    public MessageVerificationDTO(String to, String subject, String text, Map<String, String> data) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
