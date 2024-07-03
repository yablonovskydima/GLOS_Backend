package com.glos.filemanagerservice.responseMappers;

import com.accesstools.AccessNode;
import com.accesstools.AccessNodeType;
import com.accesstools.AccessReadType;
import com.glos.filemanagerservice.DTO.AccessModel;
import com.glos.filemanagerservice.DTO.RepositoryRequest;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RepositoryRequestMapper extends AbstractMapper<Repository, RepositoryRequest> {

    @Override
    protected void postEntityCopy(RepositoryRequest source, Repository destination) {
        if (source.getAccessTypes() != null) {
            destination.setAccessTypes(new HashSet<>(source.getAccessTypes().stream()
                    .map(x -> {
                        final AccessNode node = AccessNode.builder()
                                .setReadType(AccessReadType.fromName(x.getAccess()))
                                .setNodeType(AccessNodeType.fromName(x.getType()))
                                .setName(x.getName())
                                .build();
                        return new AccessType(null, node.getPattern());
                    })
                    .collect(Collectors.toSet())));
        }
        if (source.getTags() != null) {
            destination.setTags(new HashSet<>(
                    source.getTags().stream()
                            .map(x -> new Tag(null, x))
                    .toList())
            );
        }
    }

    @Override
    protected void postDtoCopy(Repository source, RepositoryRequest destination) {
        if (source.getAccessTypes() != null) {
            destination.setAccessTypes(new ArrayList<>(
                    source.getAccessTypes().stream()
                            .map(x -> {
                                final AccessNode node = AccessNode.builder(x.getName()).build();
                                final AccessModel model = new AccessModel();
                                model.setName(node.getName());
                                model.setAccess(node.getReadType().name().toLowerCase());
                                model.setType(node.getNodeType().name().toLowerCase());
                                return model;
                            })
                            .toList()
            ));
        }
        if (source.getTags() != null) {
            destination.setTags(source.getTags().stream()
                            .map(Tag::getName)
                    .toList());
        }
    }
}
