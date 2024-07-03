package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoryController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryClient repositoryClient;
    @MockBean
    private RepositoryRequestMapper requestMapper;
    @MockBean
    private RepositoryDTOMapper repositoryDTOMapper;
    @MockBean
    private RepositoryApiFacade repositoryApiFacade;
    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void getByIdTest() throws Exception {
            Long id = 1L;
            Repository repository = new Repository();
            when(repositoryClient.getRepositoryById(anyLong())).thenReturn(ResponseEntity.ok(repository));

            mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    void createTest() throws Exception {
//        Repository repository = new Repository();
//        repository.setId(1L);
//        RepositoryDTO repositoryDTO = new RepositoryDTO();
//        repositoryDTO.setId(1L);
//
//        when(repositoryApiFacade.create(any(Repository.class))).thenReturn(ResponseEntity.ok(repositoryDTO));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/repositories")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(repository)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateTest()throws Exception {
//        Repository repository = new Repository();
//        repository.setId(1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(repository)))
//                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTest()throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByOwnerIdTest() throws Exception{
        Page<RepositoryDTO> repositoryDTOPage = new Page<>();
        repositoryDTOPage.setContent(Collections.singletonList(new RepositoryDTO()));

        when(repositoryApiFacade.getRepositoryByFilter(any(Repository.class), any(Map.class), any(int.class), any(int.class), any(String.class)))
                .thenReturn(ResponseEntity.ok(repositoryDTOPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/testuser")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk());
    }

    @Test
    void getRepositoryByRootFullNameTest() throws Exception{
        Repository repository = new Repository();
        repository.setId(1L);
        when(repositoryClient.getRepositoryByRootFullName(any())).thenReturn(ResponseEntity.ok(repository));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/root-fullname/testRoot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getByFilterTest() throws Exception {
        Page<RepositoryDTO> page = new Page<>();
        page.setContent(Collections.singletonList(new RepositoryDTO()));

        when(repositoryApiFacade.getRepositoryByFilter(any(Repository.class), any(Map.class),anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(page));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}