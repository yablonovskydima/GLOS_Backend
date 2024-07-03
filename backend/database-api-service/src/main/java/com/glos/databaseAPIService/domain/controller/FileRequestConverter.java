package com.glos.databaseAPIService.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.databaseAPIService.domain.entities.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileRequestConverter implements Converter<String, List<Tag>> {

    @Override
    public List<Tag> convert(String source) {
        if (source == null) {
            return null;
        }
        try {
            return (List<Tag>) new ObjectMapper().readValue(source, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
