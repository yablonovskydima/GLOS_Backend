package com.glos.accessservice.responseMappers;

import com.accesstools.AccessNode;
import com.accesstools.AccessNodeType;
import com.accesstools.AccessReadType;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.mappers.AbstractMapper;
import com.glos.accessservice.requestDTO.AccessModel;
import org.springframework.stereotype.Component;

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
