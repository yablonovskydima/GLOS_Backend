package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import java.lang.Long;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepositoryAPIController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryService repositoryService;
    @MockBean
    private RepositoryDTOMapper mapper;
    @MockBean
    private UserDTOMapper userDTOMapper;
    @InjectMocks
    private RepositoryAPIController repositoryAPIController;
    @Autowired
    private RepositoryDTOMapper repositoryDTOMapper;

    @Test
    public void getRepositoryTest() throws Exception {
        Repository repository = new Repository();
        repository.setId(1L);
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);

        when(repositoryService.getById(1L)).thenReturn(Optional.of(repository));
        when(repositoryDTOMapper.toDto(any(Repository.class))).thenReturn(repositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createRepositoryTest() throws Exception {
        Repository repository = new Repository();
        repository.setId(1L);
        repository.setRootFullName("root/path");
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);

        when(repositoryService.create(any(Repository.class))).thenReturn(repository);
        when(repositoryDTOMapper.toDto(any(Repository.class))).thenReturn(repositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/repositories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rootFullName\":\"root/path\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteRepositoryTest() throws Exception {
        Long id = 1L;

        doNothing().when(repositoryService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/repositories/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRepositoryTest() throws Exception {
        Long id = 1L;
        Repository request = new Repository();
        Repository updated = new Repository();

        when(repositoryService.update(id , request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/repositories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void getRepositoriesByOwnerIdTest() throws Exception {
        Repository repository = new Repository();
        repository.setId(1L);
        User owner = new User();
        owner.setUsername("testUser");
        repository.setOwner(owner);

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);

        Page<Repository> repositoryPage = new PageImpl<>(Collections.singletonList(repository), PageRequest.of(0, 10), 1);
        Page<RepositoryDTO> repositoryDTOPage = new PageImpl<>(Collections.singletonList(repositoryDTO), PageRequest.of(0, 10), 1);

        when(repositoryService.findAllByFilter(any(Repository.class), any(Pageable.class), any(Map.class))).thenReturn(repositoryPage);
        when(repositoryDTOMapper.toDto(any(Repository.class))).thenReturn(repositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/testUser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
    @Test
    void getRepositoryByRootFullNameTest() throws Exception {
        Repository repository = new Repository();
        repository.setId(1L);
        repository.setRootFullName("$sys");

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);

        when(repositoryService.findByRootFullName("$sys")).thenReturn(Optional.of(repository));
        when(repositoryDTOMapper.toDto(any(Repository.class))).thenReturn(repositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/root-fullname/$sys")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getRepositoriesByFilterTest() throws Exception {
        Repository repository = new Repository();
        repository.setId(1L);

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);

        Page<Repository> repositoryPage = new PageImpl<>(Collections.singletonList(repository), PageRequest.of(0, 10), 1);
        Page<RepositoryDTO> repositoryDTOPage = new PageImpl<>(Collections.singletonList(repositoryDTO), PageRequest.of(0, 10), 1);

        when(repositoryService.findAllByFilter(any(Repository.class), any(Pageable.class), any(Map.class))).thenReturn(repositoryPage);
        when(repositoryDTOMapper.toDto(any(Repository.class))).thenReturn(repositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}