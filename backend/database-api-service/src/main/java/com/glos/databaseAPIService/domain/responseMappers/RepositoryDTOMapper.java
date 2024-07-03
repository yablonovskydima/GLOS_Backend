package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.Tag;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).collect(Collectors.toSet()));
    }

    @Override
    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).collect(Collectors.toSet()));
    }
}
