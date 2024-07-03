package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.CommentDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.CommentDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/comments")
public class CommentAPIController {

    private final CommentService commentService;
    private final CommentDTOMapper commentDTOMapper;
    private final UserDTOMapper userDTOMapper;

    public CommentAPIController(CommentService commentService, CommentDTOMapper commentDTOMapper, UserDTOMapper userDTOMapper) {
        this.commentService = commentService;
        this.commentDTOMapper = commentDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getAllByFilter(
            @ModelAttribute Comment filter,
            @RequestParam(name = "rootFullName") String rootFullName,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        final Page<Comment> comments = commentService.getPageByFilter(filter, pageable);
        final Page<CommentDTO> commentDTOPage = comments.map(commentDTOMapper::toDto);

        return ResponseEntity.ok(commentDTOPage);
    }

    @GetMapping("/path/{rootFullName}")
    public ResponseEntity<Page<CommentDTO>> getAllCommentsByRootFullName(
            @PathVariable String rootFullName,
            @ModelAttribute Comment filter,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Comment> comments = commentService.getPageCommentsByRootFullName(rootFullName, filter, pageable);
        return ResponseEntity.ok(comments.map(commentDTOMapper::toDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(
            @PathVariable Long id
    ) {
        Comment comment = commentService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Comment is not found"));
        UserDTO author = userDTOMapper.toDto(comment.getAuthor());
        CommentDTO commentDTO = commentDTOMapper.toDto(comment);
        commentDTO.setAuthor(author);
        return ResponseEntity.of(Optional.of(commentDTO));
    }

    @PostMapping
    public ResponseEntity<Comment> create(
            @RequestBody Comment request,
            UriComponentsBuilder uriBuilder
    ) {
        Comment created = commentService.create(request);
        return ResponseEntity.created(
                uriBuilder.path("/comments/{id}")
                        .build(created.getId())
        ).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Comment request
    ) {
        commentService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
