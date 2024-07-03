package com.glos.notificationservice.domain.DTO;

import com.glos.notificationservice.domain.entity.Content;

import java.util.Map;

public class MessageVerificationDTO
{
    private String to;
    private String subject;
    private String text;
    private Map<String, Object> data;


    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public MessageVerificationDTO(){}
}
