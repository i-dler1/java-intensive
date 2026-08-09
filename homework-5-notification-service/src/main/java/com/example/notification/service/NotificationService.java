package com.example.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotification(String email, String operationType) {
        String subject = "Уведомление";
        String text = switch (operationType) {
            case "CREATE" -> "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";            case "DELETE" -> "Здравствуйте! Ваш аккаунт был удалён.";
            default -> "Произошло изменение в вашем аккаунте.";
        };

        log.info("Отправка письма на {}: {}", email, text);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Письмо успешно отправлено на {}", email);
        } catch (Exception e) {
            log.error("Ошибка отправки письма на {}: {}", email, e.getMessage());
        }
    }
}