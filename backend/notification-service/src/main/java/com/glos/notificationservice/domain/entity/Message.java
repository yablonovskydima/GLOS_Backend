package com.glos.notificationservice.domain.entity;

import org.springframework.ui.Model;

public class Message
{
    private String to;
    private String subject;
    private Model data;
    private Content text;

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Model getData() {
        return data;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setData(Model data) {
        this.data = data;
    }


    public Content getText() {
        return text;
    }

    public void setText(Content text) {
        this.text = text;
    }

    public Message(String to, String subject, Content text, Model data) {
        this.to = to;
        this.subject = subject;
        this.data = data;
        this.text = text;
    }

    public Message()
    {
    }
}
