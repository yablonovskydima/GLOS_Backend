package com.glos.commentservice.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.facade.CommentApiFacade;
import com.glos.commentservice.entities.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentApiFacade commentApiFacade;

    @Test
    void getByIdTest() throws Exception {
        Long id = 1L;
        Comment comment = new Comment();

        when(commentApiFacade.getById(id)).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/comments/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(comment)));
    }
    @Test
    void createCommentTest() throws Exception {
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();

        when(commentApiFacade.createComment(anyString(), any(Comment.class))).thenReturn(comment);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(comment);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/comments")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(commentDTO)));
    }
    @Test
    void updateCommentTest() throws Exception {
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Updated comment text");

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isNoContent());

        verify(commentApiFacade, times(1)).updateComment(any(Long.class), any(Comment.class));

    }
    @Test
    void deleteCommentTest() throws Exception {
        Long id = 1L;

        when(commentApiFacade.deleteComment(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/comments/{id}", id))
                .andExpect(status().isNoContent());

        verify(commentApiFacade, times(1)).deleteComment(id);
    }
    @Test
    void getByFilterTest() throws Exception {
        Comment filter = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        List<CommentDTO> list = Collections.singletonList(commentDTO);
        Page<CommentDTO> page = new Page<>();

        when(commentApiFacade.getByFilter(anyString(), any(Map.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(page)));
    }
}