package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRequestFilterMapper extends AbstractMapper<RepositoryDTO, RepositoryRequestFilter>
{
    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;

    public RepositoryRequestFilterMapper(UserDTOMapper userDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.userDTOMapper = userDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    @Override
    protected void postDtoCopy(RepositoryDTO source, RepositoryRequestFilter destination)
    {
        destination.setOwner(source.getOwner());
        destination.setComments(source.getComments());
    }

    @Override
    protected void postEntityCopy(RepositoryRequestFilter source, RepositoryDTO destination)
    {
        destination.setOwner(source.getOwner());
        destination.setComments(source.getComments());
    }
}
