package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.responseDTO.CommentDTO;
import com.glos.databaseAPIService.domain.responseMappers.CommentDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.CommentService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CommentAPIController.class)
@ExtendWith(MockitoExtension.class)
class CommentAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentDTOMapper commentDTOMapper;
    @MockBean
    private UserDTOMapper userDTOMapper;

    @Test
    public void getAllTest() throws Exception {
            Comment comment1 = new Comment();
            comment1.setId(1L);
            comment1.setText("Test comment 1");

            List<Comment> comments = List.of(comment1);

            Pageable pageable = PageRequest.of(0, 20);
            Page<Comment> page = new PageImpl<>(comments, pageable, comments.size());

            Mockito.when(commentService.getPageByFilter(Mockito.any(), Mockito.any())).thenReturn(page);

            mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").value(List.of(comments)))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].text").value(List.of("Test comment 1")));
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        LocalDateTime dateTime = LocalDateTime.now();

        CommentDTO mockComment = new CommentDTO();
        mockComment.setId(id);
        mockComment.setText("Test Comment");
        mockComment.setCreationDate(dateTime);

        Comment comment = new Comment();
        comment.setId(id);
        comment.setText("Test Comment");
        comment.setCreationDate(dateTime);

        Mockito.when(commentService.getById(id)).thenReturn(Optional.of(comment));
        Mockito.when(commentDTOMapper.toDto(comment)).thenReturn(mockComment);

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.text", is("Test Comment")));
    }

    @Test
    void createTest() throws Exception {
        Comment request = new Comment();
        request.setText("Test comment");
        Comment created = new Comment();
        created.setId(1L);
        created.setText("Comment test num two");
        when(commentService.create(any(Comment.class))).thenReturn(created);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/comments/" + created.getId()));
    }

    @Test
    public void updateTest() throws Exception {
        Long id = 1L;
        Comment request = new Comment();
        request.setText("Updated comment");
        Comment updated = new Comment();
        updated.setId(id);
        updated.setText("Updated comment");
        when(commentService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/comments/" + id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        Long id = 1L;
        doNothing().when(commentService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/" + id))
                .andExpect(status().isNoContent());

    }
}