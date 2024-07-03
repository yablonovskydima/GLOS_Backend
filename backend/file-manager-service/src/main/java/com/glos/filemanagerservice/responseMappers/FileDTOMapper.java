package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FileDTOMapper extends AbstractMapper<File, FileDTO>
{
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final CommentDTOMapper commentDTOMapper;
    private final AccessModelMapper accessModelMapper;


    public FileDTOMapper(
            RepositoryDTOMapper repositoryDTOMapper,
            CommentDTOMapper commentDTOMapper,
            AccessModelMapper accessModelMapper) {
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
        this.accessModelMapper = accessModelMapper;
    }

    @Override
    protected void postDtoCopy(File source, FileDTO destination)
    {
        destination.setRepository(repositoryDTOMapper.toDto(source.getRepository()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
        if (source.getTags() != null) {
            destination.setTags(source.getTags().stream().map(Tag::getName).toList());
        }
        if (source.getAccessTypes() != null) {
            destination.setAccessModels(source.getAccessTypes().stream()
                    .map(accessModelMapper::toDto)
                    .toList());
        }
    }
    @Override
    protected void postEntityCopy(FileDTO source, File destination)
    {
        destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        destination.setComments(source.getComments().stream()
                .map(commentDTOMapper::toEntity)
                .collect(Collectors.toSet()));
        if (source.getTags() != null) {
            destination.setTags(source.getTags().stream().map(Tag::new).collect(Collectors.toSet()));
        }
        if (source.getAccessModels() != null) {
            destination.setAccessTypes(source.getAccessModels().stream()
                    .map(accessModelMapper::toEntity)
                    .collect(Collectors.toSet()));
        }
    }
}
