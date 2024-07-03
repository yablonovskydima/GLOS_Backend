package com.glos.databaseAPIService.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.UserService;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserAPIController.class)
class UserAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @MockBean
    private UserDTOMapper mapper;

    @Test
    void getUserTest() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        UserDTO userDTO = new UserDTO();

        when(userService.getById(id)).thenReturn(Optional.of(user));
        doNothing().when(mapper).transferEntityDto(user, userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));
    }

    @Test
    void createUserTest() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        UserDTO userDTO = new UserDTO();

        when(userService.create(any(User.class))).thenReturn(user);
        doNothing().when(mapper).transferEntityDto(user, userDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));

    }

    @Test
    void deleteUserTest() throws Exception {
        Long id = 1L;

        doNothing().when(userService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUserTest()throws Exception {
        Long id = 1L;
        User user = new User();

        when(userService.update(id, user)).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserByUsernameTest() throws Exception {
        String username = "john_doe";
        User user = new User();
        user.setUsername(username);
        UserDTO userDTO = new UserDTO();

        when(userService.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(mapper).transferEntityDto(user, userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/username/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));
    }

    @Test
    void getUserByEmailTest() throws Exception {
        String email = "john_doe@example.com";
        User user = new User();
        user.setEmail(email);
        UserDTO userDTO = new UserDTO();

        when(userService.findByEmail(email)).thenReturn(Optional.of(user));
        doNothing().when(mapper).transferEntityDto(user, userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/email/{email}", email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));
    }

    @Test
    void getUserByPhoneNumberTest() throws Exception {
        String phoneNumber = "123-456-7890";
        User user = new User();
        user.setPhone_number(phoneNumber);
        UserDTO userDTO = new UserDTO();

        when(userService.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));
        doNothing().when(mapper).transferEntityDto(user, userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/phone-number/{phoneNumber}", phoneNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));
    }
    @Test
    void getUsersByFilterTest() throws Exception {
        User filter = new User();
        User user = new User();
        Page<User> page = new PageImpl<>(Collections.singletonList(user), PageRequest.of(0, 1), 1);
        UserDTO userDTO = new UserDTO();

        when(userService.getPageByFilter(any(User.class), any(Pageable.class), eq(true))).thenReturn(page);
        when(mapper.toDto(user)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(page.map(mapper::toDto))));
    }
}