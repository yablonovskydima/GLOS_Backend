package com.glos.commentservice.domain.responseMappers;

import com.glos.commentservice.domain.DTO.RepositoryDTO;
import com.glos.commentservice.entities.Repository;
import com.glos.commentservice.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RepositoryDTOMapper extends AbstractMapper<Repository, RepositoryDTO>
{

    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;

    public RepositoryDTOMapper(UserDTOMapper userDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.userDTOMapper = userDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    @Override

    protected void postDtoCopy(Repository source, RepositoryDTO destination)
    {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
        destination.setComments(new ArrayList<>(source.getComments().stream().map(commentDTOMapper::toDto).toList()));
    }

    @Override
    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(new ArrayList<>(source.getComments().stream().map(commentDTOMapper::toEntity).toList()));
    }
}
