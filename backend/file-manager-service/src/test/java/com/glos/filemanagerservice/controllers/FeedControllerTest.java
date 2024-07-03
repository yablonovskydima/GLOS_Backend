package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.facade.FeedApiFacade;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class)
@ExtendWith(MockitoExtension.class)
class FeedControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FeedApiFacade feedApiFacade;

        @Test
        public void getPublicRepositoriesTest() throws Exception {
            Page<RepositoryDTO> mockPage = new Page<>();
            mockPage.setContent(Collections.emptyList());
            mockPage.setNumber(0);
            mockPage.setSize(10);
            mockPage.setTotalElements(0);

            ResponseEntity<Page<RepositoryDTO>> mockResponseEntity = ResponseEntity.ok(mockPage);

            when(feedApiFacade.getPublicRepos(anyInt(), anyInt(), anyString())).thenReturn(mockResponseEntity);

            mockMvc.perform(MockMvcRequestBuilders.get("/feed")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "id,asc")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("{\"content\":[],\"number\":0,\"size\":10,\"totalElements\":0}"));
        }
}