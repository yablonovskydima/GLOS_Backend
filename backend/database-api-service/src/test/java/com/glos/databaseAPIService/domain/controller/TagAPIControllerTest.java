package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Tag;
import com.glos.databaseAPIService.domain.service.TagService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(TagAPIController.class)
class TagAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;

    @Test
    void getTagById() throws Exception {
        Long id = 1L;
         Tag tag = new Tag();
         tag.setId(id);
         when(tagService.getById(id)).thenReturn(Optional.of(tag));
         mockMvc.perform(MockMvcRequestBuilders
                 .get("/tags/{id}", id))
                 .andExpect(status().isOk());

    }

    @Test
    void createTag() throws Exception {
        Tag request = new Tag();
        request.setName("PROTECTED_RW");
        Tag response = new Tag();
        response.setId(1L);
        response.setName("PROTECTED_RW");
        when(tagService.create(any(Tag.class))).thenReturn(response);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteTag() throws Exception{
        Long id = 1L;

        doNothing().when(tagService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/tags/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTag() throws Exception {
        Long id = 1L;
        Tag request = new Tag();
        Tag updated = new Tag();
        when(tagService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/tags/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTagByName() throws Exception{
        String name = "PUBLIC_R";
        Tag tag = new Tag();
        tag.setName(name);
        when(tagService.getByName(name)).thenReturn(Optional.of(tag));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tags/name/{name}", name))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTags() throws Exception{
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("PUBLIC_RW");
        List<Tag> list = List.of(tag);
        when(tagService.getAll()).thenReturn(list);

        mockMvc.perform(get("/tags", request()))
                .andExpect(status().isOk());
    }
}