package com.glos.databaseAPIService.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import com.glos.databaseAPIService.domain.responseMappers.FileDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileAPIController.class)
class FileAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileService fileService;
    @MockBean
    private FileDTOMapper fileDTOMapper;
    @MockBean
    private RepositoryDTOMapper repositoryDTOMapper;
    @MockBean
    private UserDTOMapper userDTOMapper;
    @MockBean
    private FileAPIController fileAPIController;

    @Test
    void getFileByIDTest() throws Exception {
        Long id = 1L;
        File file = new File();
        FileDTO fileDTO = new FileDTO();
        file.setId(id);
        when(fileService.getById(id)).thenReturn(Optional.of(file));
        doNothing().when(fileDTOMapper).transferEntityDto(file, fileDTO);
        mockMvc.perform(MockMvcRequestBuilders.
                get("/files/{id}" , id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createFileTest() throws Exception{
        Long id = 1L;
        File file = new File();
        file.setId(id);
        FileDTO fileDTO = new FileDTO();
        when(fileService.create(any(File.class))).thenReturn(file);
        when(fileAPIController.transferEntityDTO(any(File.class), same(fileDTO))).thenReturn(fileDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(file);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/files")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 201));
    }

    @Test
    void updateFileTest() throws Exception {
        Long id = 1L;
        File file = new File();
        file.setId(id);

        when(fileService.update(id, file)).thenReturn(file);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(file);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/files/{id}", id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 204));
    }


    @Test
    void deleteFileTest() throws Exception {
        Long id = 1L;

        doNothing().when(fileService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/files/{id}", id))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 204));
    }

    @Test
    void getFilesByRepositoryTest() throws Exception {
        Long id = 1L;
        Repository repository = new Repository();
        repository.setId(id);
        File file = new File();
        file.setId(id);
        Page<File> filesPage = Page.empty();
        Page<FileDTO> filesDTOPage = filesPage.map(fileDTOMapper::toDto);

        when(fileService.findAllByRepository(any(File.class), any(Pageable.class))).thenReturn(filesPage);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/files/repository/{repositoryId}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getFileByRootFullNameTest() throws Exception {
        Long repositoryId = 1L;
        Repository repository = new Repository();
        repository.setId(repositoryId);
        File file = new File();
        file.setRepository(repository);
        FileDTO fileDTO = new FileDTO();

        Page<File> filesPage = Page.empty();
        Page<FileDTO> filesDTOPage = filesPage.map(fileDTOMapper::toDto);

        when(fileService.findAllByRepository(any(File.class), any(Pageable.class))).thenReturn(filesPage);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/files/root-fullname/{rootFullName}", repositoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getFilesByFilterTest() throws Exception {
        File filter = new File();
        FileDTO fileDTO = new FileDTO();

        Page<File> filesPage = Page.empty();
        Page<FileDTO> filesDTOPage = filesPage.map(fileDTOMapper::toDto);

        when(fileService.findAllByFilter(any(File.class), any(Pageable.class))).thenReturn(filesPage);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/files")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}