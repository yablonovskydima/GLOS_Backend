package com.glos.databaseAPIService.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.service.AccessTypeService;
import org.junit.jupiter.api.Test;
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
@WebMvcTest(AccessTypeAPIController.class)
public class AccessTypeAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessTypeService accessTypeService;


    @Test
    void getAllTest() throws Exception {
        AccessType accessType = new AccessType();
        accessType.setId(1L);
        accessType.setName("PUBLIC_RW");
        List<AccessType> list = List.of(accessType);
        when(accessTypeService.getAll()).thenReturn(list);

        mockMvc.perform(get("/access-types", request()))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        AccessType accessType = new AccessType();
        accessType.setId(id);
        when(accessTypeService.getById(id)).thenReturn(Optional.of(accessType));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/access-types/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getNameTest() throws Exception {
        String name = "PUBLIC_R";
        AccessType accessType = new AccessType();
        accessType.setName(name);
        when(accessTypeService.getByName(name)).thenReturn(Optional.of(accessType));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/access-types/name/{name}", name))
                .andExpect(status().isOk());
    }

    @Test
    public void create_ValidAccessType_ReturnsCreatedTest() throws Exception {
        AccessType request = new AccessType();
        request.setName("PUBLIC_RW");
        AccessType response = new AccessType();
        response.setId(1L);
        response.setName("PUBLIC_RW");
        when(accessTypeService.create(any(AccessType.class))).thenReturn(response);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/access-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("PUBLIC_RW"));
    }

    @Test
    public void updateTest() throws Exception {
        Long id = 1L;
        AccessType request = new AccessType();
        AccessType updated = new AccessType();
        when(accessTypeService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/access-types/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        Long id = 1L;

        doNothing().when(accessTypeService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/access-types/{id}", id))
                .andExpect(status().isNoContent());
    }
}