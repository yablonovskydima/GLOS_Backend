package com.glos.notificationservice.domain.DTO;

import com.glos.notificationservice.domain.entity.Content;

import java.util.Map;

public class MessageDTO
{
    private String to;
    private String subject;
    private Content text;
    private Map<String, String> data;

    public String getTo() {
        return to;
    }

    public Content getText() {
        return text;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getSubject() {
        return subject;
    }
}
