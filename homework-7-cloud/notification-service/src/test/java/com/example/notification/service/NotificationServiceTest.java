package com.example.notification.service;

import com.example.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void sendNotification_ShouldSendEmail() {
        // given
        String email = "test@mailhog.local";
        String operationType = "CREATE";

        // when
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.sendNotification(email, operationType);

        // then
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}