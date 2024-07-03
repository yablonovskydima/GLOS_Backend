package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.entities.File;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FileRequestConverter implements Converter<String, File> {

    @Override
    public File convert(String source) {
        if (source == null) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(source, File.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
