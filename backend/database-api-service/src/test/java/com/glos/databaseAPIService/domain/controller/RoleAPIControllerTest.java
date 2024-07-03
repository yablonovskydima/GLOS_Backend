package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Role;
import com.glos.databaseAPIService.domain.service.RoleService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
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


import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleAPIController.class)
@ExtendWith(MockitoExtension.class)
class RoleAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleService roleService;

    @Test
    public void getRoleByIdTest() throws Exception {
        Long id = 1L;
        Role role = new Role();
        role.setId(id);

        when(roleService.getById(id)).thenReturn(Optional.of(role));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/roles/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(role)));
    }

    @Test
    void createRole() throws Exception{
        Long id = 1L;
        Role role = new Role();
        role.setId(id);

        when(roleService.create(any(Role.class))).thenReturn(role);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(role);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/roles")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(role)));
    }

    @Test
    void deleteRole() throws Exception{
        Long id = 1L;

        doNothing().when(roleService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/roles/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRole() throws Exception{
        Long id =1L;
        Role request = new Role();
        Role updated = new Role();

        when(roleService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/roles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }
    @Test
    void getRoleByName() throws Exception{
        String name = "ADMIN";
        Role role = new Role();
        role.setName(name);

        when(roleService.findByName(name)).thenReturn(Optional.of(role));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/roles/name/{name}", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(role)));
    }
    @Test
    void getPageTest() throws Exception{
        Role role = new Role();
        Page<Role> page = new PageImpl<>(Collections.singletonList(role), PageRequest.of(0, 1), 1);

        when(roleService.getPage(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/roles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(page)));
    }

}
