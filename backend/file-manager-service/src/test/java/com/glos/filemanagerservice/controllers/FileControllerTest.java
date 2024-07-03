package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileAndStatus;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.FileUpdateRequest;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileClient fileClient;

    @MockBean
    private RepositoryClient repositoryClient;

    @MockBean
    private FileDTOMapper fileDTOMapper;

    @MockBean
    private FileRequestMapper fileRequestMapper;

    @MockBean
    private FileApiFacade fileApiFacade;

    @Test
    void getFileByIdTest() throws Exception {
        File fileDTO = new File();
        fileDTO.setId(1L);
        when(fileClient.getFileByID(anyLong())).thenReturn(ResponseEntity.ok(fileDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateTest() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "fileData", "filename.txt", "text/plain", "some content".getBytes());

        FileUpdateRequest.FileNode fileNode = new FileUpdateRequest.FileNode(1L, "fileBody", mockFile);
        FileUpdateRequest updateRequest = new FileUpdateRequest(List.of(fileNode));

        List<FileAndStatus> response = List.of(new FileAndStatus());
        when(fileApiFacade.update(any(FileUpdateRequest.class))).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(MockMvcRequestBuilders.put("/files/id/{id}", 1L)
                        .param("fileNodes[0].id", "1")
                        .param("fileNodes[0].fileBody", "fileBody")
                        .contentType("multipart/form-data")
                        .content(mockFile.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        List<String> rootFullNames = List.of("root1", "root2");
        List<FileAndStatus> response = List.of(new FileAndStatus());
        when(fileApiFacade.deleteFiles(rootFullNames)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(MockMvcRequestBuilders.delete("/files")
                        .content("[\"root1\", \"root2\"]")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getByRootFullNameTest() throws Exception {
        File fileDTO = new File();
        fileDTO.setId(1L);
        when(fileClient.getFileByRootFullName(any())).thenReturn(ResponseEntity.ok(fileDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/someRootFullName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getByRepositoryTest() throws Exception {
        Page<FileDTO> mockPage = new Page<>();
        mockPage.setContent(Collections.emptyList());
        mockPage.setNumber(0);
        mockPage.setSize(10);
        mockPage.setTotalElements(0);

        when(fileApiFacade.getFileByRepository(anyLong(), anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(mockPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/repository/{repositoryId}", 1L)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[],\"number\":0,\"size\":10,\"totalElements\":0}"));
    }

    @Test
    void getByFilterTest() throws Exception {
        Page<FileDTO> mockPage = new Page<>();
        mockPage.setContent(Collections.emptyList());
        mockPage.setNumber(0);
        mockPage.setSize(10);
        mockPage.setTotalElements(0);

        when(fileApiFacade.getFilesByFilter(any(File.class), anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(mockPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[],\"number\":0,\"size\":10,\"totalElements\":0}"));
    }
}