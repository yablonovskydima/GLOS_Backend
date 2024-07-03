package com.glos.accessservice.responseMappers;

import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.mappers.AbstractMapper;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDTOMapper extends AbstractMapper<Repository, RepositoryDTO>
{

    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;
    private final AccessModelMapper accessModelMapper;

    public RepositoryDTOMapper(
            UserDTOMapper userDTOMapper,
            CommentDTOMapper commentDTOMapper,
            AccessModelMapper accessModelMapper) {
        this.userDTOMapper = userDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
        this.accessModelMapper = accessModelMapper;
    }

    @Override

    protected void postDtoCopy(Repository source, RepositoryDTO destination)
    {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
        if (source.getAccessTypes() != null) {
            destination.setAccessModels(source.getAccessTypes().stream()
                    .map(accessModelMapper::toDto)
                    .toList());
        }
    }

    @Override
    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).toList());
        if (source.getAccessModels() != null) {
            destination.setAccessTypes(source.getAccessModels().stream()
                            .map(accessModelMapper::toEntity)
                    .toList());
        }
    }
}
