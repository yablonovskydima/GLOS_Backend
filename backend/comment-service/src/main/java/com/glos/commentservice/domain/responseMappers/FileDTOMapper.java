package com.glos.commentservice.domain.responseMappers;

import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.entities.File;
import com.glos.commentservice.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FileDTOMapper extends AbstractMapper<File, FileDTO>
{
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final CommentDTOMapper commentDTOMapper;


    public FileDTOMapper(RepositoryDTOMapper repositoryDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    @Override
    protected void postDtoCopy(File source, FileDTO destination)
    {
        destination.setRepository(repositoryDTOMapper.toDto(source.getRepository()));
        destination.setComments(new ArrayList<>(source.getComments().stream().map(commentDTOMapper::toDto).toList()));
    }
    @Override
    protected void postEntityCopy(FileDTO source, File destination)
    {
        destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        destination.setComments(new ArrayList<>(source.getComments().stream().map(commentDTOMapper::toEntity).toList()));
    }
}
