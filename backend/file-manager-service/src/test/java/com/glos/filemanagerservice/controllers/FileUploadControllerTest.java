package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.DownloadRequest;
import com.glos.filemanagerservice.DTO.FileAndStatus;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.FileRequest;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.FileStorageClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
@ExtendWith(MockitoExtension.class)

class FileUploadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileApiFacade fileApiFacade;

    @MockBean
    private RepositoryStorageClient repositoryStorageClient;

    @MockBean
    private FileClient fileClient;

    @MockBean
    private FileStorageClient fileStorageClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uploadFileTest()throws Exception {
        FileAndStatus fileAndStatus = new FileAndStatus();
        List<FileAndStatus> fileAndStatuses = Collections.singletonList(fileAndStatus);
        when(fileApiFacade.uploadFiles(any())).thenReturn(ResponseEntity.ok(fileAndStatuses));

        FileRequest fileRequest = new FileRequest();

        mockMvc.perform(MockMvcRequestBuilders.put("/files/upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("fileRequests", fileRequest))
                .andExpect(status().isOk());
    }
    @Test
    void downloadFileTest()throws Exception {
        byte[] fileContent = "test content".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        ResponseEntity<ByteArrayResource> responseEntity = ResponseEntity.ok(resource);

        InputStream inputStream = new ByteArrayInputStream(fileContent);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        File file = new File();

        when(fileApiFacade.downloadFiles(any(DownloadRequest.class))).thenReturn(inputStreamResource);
        DownloadRequest downloadRequest = new DownloadRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/files/download")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"yourJsonField\": \"yourValue\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/zip"));
    }
    @Test
    void downloadRepository() throws Exception {
        ByteArrayResource resource = new ByteArrayResource("repository content".getBytes());
        Response response = Response.builder().status(200).build();
        when(repositoryStorageClient.getRepository(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/someRootFullName/download"))
                .andExpect(status().isOk())
                .andExpect(content().string("repository content"));
    }
}