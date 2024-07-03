package com.glos.filemanagerservice.utils;

import com.accesstools.AccessNode;
import com.glos.filemanagerservice.entities.AccessType;

import java.util.Map;

public class AccessDtoUtil {

    public Map.Entry<String, String> toAccessDto(AccessType accessType) {
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        AccessNode node = AccessNode.builder(accessType.getName()).build();



        return Map.entry(keyBuilder.toString(), valueBuilder.toString());
    }

}
