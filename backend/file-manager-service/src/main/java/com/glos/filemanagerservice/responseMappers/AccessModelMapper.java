package com.glos.filemanagerservice.responseMappers;

import com.accesstools.AccessNode;
import com.accesstools.AccessNodeType;
import com.accesstools.AccessReadType;
import com.accesstools.AccessUtils;
import com.glos.filemanagerservice.DTO.AccessModel;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccessModelMapper extends AbstractMapper<AccessType, AccessModel> {

    @Override
    protected void copyDto(AccessType source, AccessModel destination, boolean hardSet) {
        if (hardSet || source.getName() != null) {
            final AccessNode node = AccessNode.builder(source.getName()).build();
            destination.setType(node.getNodeType().name());
            destination.setName(node.getName());
            if (node.getReadType() == AccessReadType.R) {
                destination.setAccess("READ");
            } else if (node.getReadType() == AccessReadType.RW) {
                destination.setAccess("READWRITE");
            } else {
                destination.setAccess("NONE");
            }
            if (node.getList() != null && !node.getList().isEmpty()) {
                destination.setElements(node.getList());
            } else {
                destination.setElements(new ArrayList<>());
            }
        }
    }

    @Override
    protected void copyEntity(AccessModel source, AccessType destination, boolean hardSet) {
        AccessNode node = AccessNode.builder()
                .setReadType(AccessReadType.fromName(source.getAccess()))
                .setNodeType(AccessNodeType.fromName(source.getType()))
                .setName(source.getName())
                .build();
        destination.setName(node.getPattern());
    }
}
