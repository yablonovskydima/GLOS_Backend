package com.glos.accessservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.accessservice.facade.AccessFacade;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.requestDTO.AccessModel;
import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.responseDTO.Pageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccessController.class)
@ExtendWith(MockitoExtension.class)
class AccessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessFacade accessFacade;

    @InjectMocks
    private AccessController accessController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void IsAvailableTest() throws Exception {
        String username = "sys";
        String access = "read";
        String rootFullName = "$sys";

        when(accessFacade.available(any(AccessRequest.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/access/{access}/{rootFullName}/available/{username}", access, rootFullName, username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void AddAccessTest() throws Exception {
        AccessModel model = new AccessModel();
        String rootFullName = "$sys";

        ObjectMapper objectMapper = new ObjectMapper();
        when(accessFacade.addAccess(eq(rootFullName), any(AccessModel.class))).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(MockMvcRequestBuilders.put("/access/{rootFullName}/add-access", rootFullName)
                        .content(objectMapper.writeValueAsString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void RemoveAccessTest() throws Exception {
        AccessModel model = new AccessModel();
        String rootFullName = "$sys";

        ObjectMapper objectMapper = new ObjectMapper();
        when(accessFacade.removeAccess(eq(rootFullName), any(AccessModel.class))).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(MockMvcRequestBuilders.put("/access/{rootFullName}/remove-access", rootFullName)
                        .content(objectMapper.writeValueAsString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetAccessesTest() throws Exception {
        Page<AccessModel> page = new Page<>();
        when(accessFacade.accessPage(eq("$test%root"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/access/$test%root/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk());
    }
}