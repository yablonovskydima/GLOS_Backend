package com.glos.filemanagerservice.responseMappers;

import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

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
        destination.setTags(new ArrayList<>(source.getTags().stream().map(Tag::getName).toList()));
    }

    @Override
    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(new HashSet<>(
                source.getComments().stream().map(commentDTOMapper::toEntity).collect(Collectors.toSet())
        ));
        destination.setTags(new HashSet<>(
                source.getTags().stream().map(x -> new Tag(null, x)).collect(Collectors.toSet())
        ));
    }
}
