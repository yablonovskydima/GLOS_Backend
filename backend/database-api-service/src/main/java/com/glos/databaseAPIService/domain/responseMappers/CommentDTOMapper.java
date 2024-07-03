package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOMapper extends AbstractMapper<Comment, CommentDTO>
{
    private final UserDTOMapper userMapper;

    public CommentDTOMapper(UserDTOMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    protected void postDtoCopy(Comment source, CommentDTO destination)
    {
        destination.setAuthor(userMapper.toDto(source.getAuthor()));
    }
    @Override
    protected void postEntityCopy(CommentDTO source, Comment destination)
    {
        destination.setAuthor(userMapper.toEntity(source.getAuthor()));
    }

}
