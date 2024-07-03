package com.glos.commentservice.domain.responseMappers;

import com.glos.commentservice.domain.DTO.CommentDTO;

import com.glos.commentservice.entities.Comment;
import com.glos.commentservice.entities.mappers.AbstractMapper;
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
