package com.example.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDto {

    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotBlank(message = "Operation type не может быть пустым")
    private String operationType;
}