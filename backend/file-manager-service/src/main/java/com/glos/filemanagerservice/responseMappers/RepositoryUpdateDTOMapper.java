package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.RepositoryUpdateDTO;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;


@Component
public class RepositoryUpdateDTOMapper extends AbstractMapper<Repository, RepositoryUpdateDTO> {

    @Override
    protected void postEntityCopy(RepositoryUpdateDTO source, Repository destination) {
        destination.setTags(new HashSet<>(source.getTags().stream().map(Tag::new).collect(Collectors.toSet())));
    }

    @Override
    protected void postDtoCopy(Repository source, RepositoryUpdateDTO destination) {
        destination.setTags(new ArrayList<>(source.getTags().stream().map(Tag::getName).toList()));
    }

}
