package com.glos.databaseAPIService.domain.service;


import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.entityMappers.CommentMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.CommentRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@Service
public class CommentService implements CrudService<Comment, Long> {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(
            CommentRepository commentRepository,
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional
    @Override
    public Comment create(Comment e) {
        System.out.println(e);
        return commentRepository.save(e);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getAll(Comment filter) {
        return commentRepository.findAll(Example.of(filter));
    }

    @Override
    public List<Comment> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Comment> getById(Long aLong) {
        return commentRepository.findById(aLong);
    }

    public Page<Comment> getPageByFilter(Comment comment, Pageable pageable)
    {
        return commentRepository.findAll(Example.of(comment), pageable);
    }

    public Page<Comment> getPageCommentsByRootFullName(String rootFullName, Comment filter, Pageable pageable) {
        final List<Comment> comments = commentRepository.findAllByResourcePath(rootFullName, pageable).stream()
                .filter(x -> filter.getId() == null || x.getId().equals(filter.getId()))
                .filter(x -> filter.getAuthor() == null || (
                        x.getAuthor().getId().equals(filter.getAuthor().getId()) ||
                                x.getAuthor().getUsername().equals(filter.getAuthor().getUsername())
                ))
                .toList();

        return new PageImpl<>(comments, pageable, comments.size());
    }

    @Transactional
    @Override
    public Comment update(Long aLong, Comment e) {
        Optional<Comment> commentOpt = getById(aLong);
        Comment found = commentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        commentMapper.transferDtoEntity(e, found);
        return commentRepository.save(found);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        Optional<Comment> commentOpt = getById(aLong);
        Comment found = commentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        commentRepository.deleteById(found.getId());
    }
}
