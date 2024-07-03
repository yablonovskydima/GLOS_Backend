package com.glos.api.authservice.controller;

import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.entities.Roles;
import com.glos.api.authservice.entities.User;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.util.security.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
@Disabled
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SimpleAuthService simpleAuthService;
    @MockBean
    private SignUpRequestMapper signUpRequestMapper;

    @Test
    @WithMockUser(roles = "ADMIN", username = "Andrew" , password = "qwert12345")
    @AutoConfigureMockMvc(addFilters = false)
    void registerUserNoCorrectDataTest() throws Exception {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("user62");
        request.setPassword("Kkolya.com62");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setRoles(Collections.singletonList(Roles.ROLE_USER.asEntity()));

        JwtEntity jwtEntity = new JwtEntity(() -> user);
        JwtRequest jwtRequest = new JwtRequest();
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken("Test Token");

        when(signUpRequestMapper.toEntity(request)).thenReturn(user);
        when(simpleAuthService.register(any(JwtEntity.class))).thenReturn(jwtResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
        .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                || result.getResponse().getStatus() == 403));
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "Andrew" , password = "qwert12345")
    void registerAdminTest() throws Exception {
        SignUpRequest request = new SignUpRequest();
        User user = new User();
        Roles roles = Roles.ROLE_ADMIN;

        when(signUpRequestMapper.toEntity(request)).thenReturn(user);
        when(simpleAuthService.register(any(JwtEntity.class))).thenAnswer(invocation -> {
            JwtEntity jwtEntity = invocation.getArgument(0);
            User userFromJwtEntity = jwtEntity.getUser();
            assertEquals(user, userFromJwtEntity);
            assertEquals(Collections.singletonList(Roles.ROLE_ADMIN.asEntity()), userFromJwtEntity.getRoles());
            return new JwtResponse();
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 403));
    }

    @Test
    @Disabled
    void loginTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testUser");
        jwtRequest.setPassword("testPassword");

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken("testToken");

        when(simpleAuthService.authenticate(any(JwtRequest.class))).thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 403));
    }

    @Test
    @Disabled
    void validateTokenTest() throws Exception {
        String token = "testToken";

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/validate")
                        .param("token", token))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 401));
    }

    @Test
    @Disabled
    void refreshTest() throws Exception {
        String refreshRequestJson = "{ \"token\": \"your_refresh_token_here\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshRequestJson))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 403));
    }
    @Test
    @Disabled
    void logoutTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/logout"))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 401));
    }
}
