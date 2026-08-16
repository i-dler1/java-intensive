package com.example.user.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class UserResponseDto extends RepresentationModel<UserResponseDto> {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
}