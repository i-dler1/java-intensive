package com.example.notification.controller;

import com.example.notification.dto.EmailRequestDto;
import com.example.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendEmail_ShouldReturnOk() throws Exception {
        EmailRequestDto request = new EmailRequestDto();
        request.setEmail("test@example.com");
        request.setOperationType("CREATE");

        doNothing().when(notificationService).sendNotification(anyString(), anyString());

        mockMvc.perform(post("/api/notify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void sendEmail_ShouldReturn400_WhenEmailIsEmpty() throws Exception {
        EmailRequestDto request = new EmailRequestDto();
        request.setEmail("");
        request.setOperationType("CREATE");

        mockMvc.perform(post("/api/notify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sendEmail_ShouldReturn500_WhenServiceThrowsException() throws Exception {
        EmailRequestDto request = new EmailRequestDto();
        request.setEmail("test@example.com");
        request.setOperationType("CREATE");

        doThrow(new RuntimeException("Mail service error"))
                .when(notificationService).sendNotification(anyString(), anyString());

        mockMvc.perform(post("/api/notify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}