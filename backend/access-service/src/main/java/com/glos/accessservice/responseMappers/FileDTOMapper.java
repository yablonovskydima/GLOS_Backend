package com.glos.accessservice.responseMappers;

import com.glos.accessservice.entities.File;
import com.glos.accessservice.mappers.AbstractMapper;
import com.glos.accessservice.responseDTO.FileDTO;
import org.springframework.stereotype.Component;

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
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
    }
    @Override
    protected void postEntityCopy(FileDTO source, File destination)
    {
        destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).toList());
    }
}
