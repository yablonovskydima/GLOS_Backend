package com.glos.accessservice.responseMappers;

import com.glos.accessservice.entities.Comment;
import com.glos.accessservice.mappers.AbstractMapper;
import com.glos.accessservice.responseDTO.CommentDTO;
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
        destination.setDate(source.getCreationDate());
        destination.setUpdateDate(source.getUpdateDate());
    }
    @Override
    protected void postEntityCopy(CommentDTO source, Comment destination)
    {
        destination.setAuthor(userMapper.toEntity(source.getAuthor()));
        destination.setCreationDate(source.getDate());
        destination.setUpdateDate(source.getUpdateDate());
    }
}
