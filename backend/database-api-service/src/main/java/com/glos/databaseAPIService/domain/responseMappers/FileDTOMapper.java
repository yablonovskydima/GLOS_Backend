package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).collect(Collectors.toSet()));
    }
    @Override
    protected void postEntityCopy(FileDTO source, File destination)
    {
        destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).collect(Collectors.toSet()));
    }
}

