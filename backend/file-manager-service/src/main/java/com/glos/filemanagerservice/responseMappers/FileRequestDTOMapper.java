package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.FileRequestDTO;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class FileRequestDTOMapper extends AbstractMapper<File, FileRequestDTO> {

    private final RepositoryDTOMapper repositoryDTOMapper;

    private final AccessModelMapper accessModelMapper;

    public FileRequestDTOMapper(
            RepositoryDTOMapper repositoryDTOMapper,
            AccessModelMapper accessModelMapper) {
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.accessModelMapper = accessModelMapper;
    }

    @Override
    protected void postEntityCopy(FileRequestDTO source, File destination) {
        if (source.getRepository() != null) {
            destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        }
        if (source.getTags() != null) {
            destination.setTags(new HashSet<>((source.getTags().stream().map(Tag::new).collect(Collectors.toSet()))));
        }
        if (source.getAccessModels() != null) {
            destination.setAccessTypes(new HashSet<>(source.getAccessModels().stream().map(accessModelMapper::toEntity).collect(Collectors.toSet())));
        }
    }

    @Override
    protected void postDtoCopy(File source, FileRequestDTO destination) {
        if (source.getRepository() != null) {
            destination.setRepository(repositoryDTOMapper.toDto(source.getRepository()));
        }
        if (source.getTags() != null) {
            destination.setTags(new ArrayList<>(source.getTags().stream().map(Tag::getName).toList()));
        }
        if (source.getAccessTypes() != null) {
            destination.setAccessModels(new ArrayList<>(source.getAccessTypes().stream().map(accessModelMapper::toDto).collect(Collectors.toSet())));
        }
    }
}
