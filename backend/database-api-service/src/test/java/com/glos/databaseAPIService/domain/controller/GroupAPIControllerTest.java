package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import com.glos.databaseAPIService.domain.responseMappers.GroupDTOMapper;
import com.glos.databaseAPIService.domain.service.GroupService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupAPIController.class)
@ExtendWith(MockitoExtension.class)
class GroupAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @MockBean
    private GroupDTOMapper groupDTOMapper;
    @Test
    void getGroupByIdTest() throws Exception {
        Long id = 1L;
        Group group = new Group();
        GroupDTO groupDTO = new GroupDTO();

        when(groupService.getById(id)).thenReturn(Optional.of(group));
        when(groupDTOMapper.toDto(group)).thenReturn(groupDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/groups/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(groupDTO)));
    }

    @Test
    void createGroupTest() throws Exception {
        Group request = new Group();
        Group created = new Group();
        created.setId(1L);

        when(groupService.create(ArgumentMatchers.any(Group.class))).thenReturn(created);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(groupService, times(1)).create(ArgumentMatchers.any(Group.class));
    }

    @Test
    void deleteGroupTest() throws Exception{
        Long id = 1L;
        doNothing().when(groupService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/{id}" , id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroupTest() throws Exception {
        Long id = 1L;
        Group request = new Group();
        Group updated = new Group();
        updated.setId(id);
        when(groupService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/groups/{id}" , id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void getAllGroupsTest() throws  Exception {
        Group group = new Group();
        group.setId(1L);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(1L);

        Page<Group> groupPage = new PageImpl<>(Collections.singletonList(group), PageRequest.of(0, 10), 1);
        Page<GroupDTO> groupDTOPage = new PageImpl<>(Collections.singletonList(groupDTO), PageRequest.of(0, 10), 1);

        when(groupService.getPage(any(Pageable.class), eq(true))).thenReturn(groupPage);
        when(groupDTOMapper.toDto(any(Group.class))).thenReturn(groupDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getGroupsByFiltersTest() throws Exception {
        Group group = new Group();
        group.setId(1L);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(1L);

        Page<Group> groupPage = new PageImpl<>(Collections.singletonList(group), PageRequest.of(0, 10), 1);
        Page<GroupDTO> groupDTOPage = new PageImpl<>(Collections.singletonList(groupDTO), PageRequest.of(0, 10), 1);

        when(groupService.getPageByFilter(any(Group.class), any(Pageable.class), eq(true))).thenReturn(groupPage);
        when(groupDTOMapper.toDto(any(Group.class))).thenReturn(groupDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/groups/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}