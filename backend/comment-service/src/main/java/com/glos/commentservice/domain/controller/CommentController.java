package com.glos.commentservice.domain.controller;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.facade.CommentApiFacade;
import com.glos.commentservice.domain.responseMappers.CommentDTOMapper;
import com.glos.commentservice.domain.utils.MapUtils;
import com.glos.commentservice.domain.validation.OnCreate;
import com.glos.commentservice.domain.validation.OnUpdate;
import com.glos.commentservice.entities.Comment;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    private final CommentApiFacade commentApiFacade;
    private final CommentDTOMapper commentDTOMapper;

    public CommentController(
            CommentApiFacade commentApiFacade,
            CommentDTOMapper commentDTOMapper) {
        this.commentApiFacade = commentApiFacade;
        this.commentDTOMapper = commentDTOMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id)
    {
        final Comment comment = commentApiFacade.getById(id);
        final CommentDTO commentDTO = commentDTOMapper.toDto(comment);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id,
                                           @RequestBody @Validated(OnUpdate.class) CommentDTO request)
    {
        Comment comment = commentDTOMapper.toEntity(request);
        return commentApiFacade.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id)
    {
        return commentApiFacade.deleteComment(id);
    }

    @PostMapping("/{rootFullName}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable
            @NotBlank(message = "rootFullName can't be empty")
            String rootFullName,
            @RequestBody
            @Validated(OnCreate.class)
            CommentDTO request,
            UriComponentsBuilder uriBuilder) {
        final Comment comment = commentDTOMapper.toEntity(request);
        final Comment created = commentApiFacade.createComment(rootFullName, comment);
        final CommentDTO commentDTO = commentDTOMapper.toDto(created);
        return ResponseEntity.created(uriBuilder.path("/comments/{id}")
                        .build(created.getId()))
                .body(commentDTO);
    }

    @GetMapping("/{rootFullName}/list")
    public ResponseEntity<Page<CommentDTO>> getByFilter(
            @PathVariable
            @NotBlank(message = "rootFullName can't be empty")
            String rootFullName,
            @ModelAttribute Comment comment,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        final Map<String, Object> filter = MapUtils.map(comment);
        filter.put("page", page);
        filter.put("size", size);
        filter.put("sort", sort);
        final Page<Comment> commentPage = commentApiFacade.getByFilter(rootFullName, filter);
        commentPage.setNumber(page);
        commentPage.setSize(size);
        commentPage.setSortPattern(sort);
        commentPage.setTotalElements(commentPage.getContent().size());
        return ResponseEntity.ok(commentPage.map(commentDTOMapper::toDto));
    }

}
