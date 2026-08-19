package com.example.notification.controller;

import com.example.notification.dto.EmailRequestDto;
import com.example.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void sendEmail(@Valid @RequestBody EmailRequestDto request) {
        notificationService.sendNotification(request.getEmail(), request.getOperationType());
    }
}