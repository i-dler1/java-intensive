package com.example.user.controller;

import com.example.user.dto.UserRequestDto;
import com.example.user.dto.UserResponseDto;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        UserRequestDto request = new UserRequestDto();
        request.setName("Анна");
        request.setEmail("anna@mail.com");
        request.setAge(25);

        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setName("Анна");
        response.setEmail("anna@mail.com");
        response.setAge(25);
        response.setCreatedAt(LocalDateTime.now());

        when(userService.createUser(any(UserRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Анна"));
    }

    @Test
    void getUser_ShouldReturnUser_WhenExists() throws Exception {
        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setName("Анна");
        response.setEmail("anna@mail.com");
        response.setAge(25);
        response.setCreatedAt(LocalDateTime.now());

        when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Анна"));
    }

    @Test
    void getUser_ShouldReturn404_WhenUserNotFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new RuntimeException("User not found with id: 99"));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserRequestDto request = new UserRequestDto();
        request.setName("Анна Иванова");
        request.setEmail("anna.new@mail.com");
        request.setAge(26);

        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setName("Анна Иванова");
        response.setEmail("anna.new@mail.com");
        response.setAge(26);
        response.setCreatedAt(LocalDateTime.now());

        when(userService.updateUser(eq(1L), any(UserRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Анна Иванова"));
    }

    @Test
    void updateUser_ShouldReturn404_WhenUserNotFound() throws Exception {
        UserRequestDto request = new UserRequestDto();
        request.setName("Анна");
        request.setEmail("anna@mail.com");
        request.setAge(25);

        when(userService.updateUser(eq(99L), any(UserRequestDto.class)))
                .thenThrow(new RuntimeException("User not found with id: 99"));

        mockMvc.perform(put("/api/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_ShouldReturn400_WhenNameIsEmpty() throws Exception {
        UserRequestDto request = new UserRequestDto();
        request.setName("");
        request.setEmail("anna@mail.com");
        request.setAge(25);

        when(userService.createUser(any(UserRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Имя не может быть пустым"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturn400_WhenEmailIsInvalid() throws Exception {
        UserRequestDto request = new UserRequestDto();
        request.setName("Анна");
        request.setEmail("invalid-email");
        request.setAge(25);

        when(userService.createUser(any(UserRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Некорректный формат email"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}