package com.glos.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.notificationservice.domain.DTO.MessageHtmlDTO;
import com.glos.notificationservice.domain.DTO.MessageVerificationDTO;
import com.glos.notificationservice.domain.DTO.SimpleMessageDTO;
import com.glos.notificationservice.domain.service.EmailService;
import com.glos.notificationservice.domain.template.base.MessageTemplate;
import com.glos.notificationservice.domain.template.base.TemplateNames;
import com.glos.notificationservice.manager.EmailManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;
    @MockBean
    private EmailManager emailManager;
    ObjectMapper mapper = new ObjectMapper();
    @Test
    void getAllTemplatesTest() throws Exception {
        List<String> expectedTemplates = Arrays.stream(TemplateNames.values()).map(Enum::toString).toList();

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.get("/email/templates")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedTemplates)));
    }

    @Test
    void sendMessageTest() throws Exception{
        MessageVerificationDTO messageDTO = new MessageVerificationDTO();

        Mockito.doNothing().when(emailManager).send(Mockito.any(MessageTemplate.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/email/send-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(messageDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testSendMessageTest() throws Exception{
        SimpleMessageDTO messageDTO = new SimpleMessageDTO(
                "test@example.com",
                "Subject",
                "Text"
        );

        Mockito.doNothing().when(emailManager).send(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(messageDTO)))
                .andExpect(status().isOk());
    }


    @Test
    void sendHtmlTest() throws Exception {
        MessageHtmlDTO messageHtmlDTO = new MessageHtmlDTO(
                "test@example.com",
                "Subject",
                "<html>Text</html>",
                Map.of("key", "value")
        );

        Mockito.doNothing().when(emailManager).send(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send-html")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(messageHtmlDTO)))
                .andExpect(status().isOk());
    }
}