package com.example.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(Map<String, String> event) {
        String email = event.get("email");
        String operationType = event.get("operationType");
        log.info("Получено событие из Kafka: email={}, operationType={}", email, operationType);
        notificationService.sendNotification(email, operationType);
    }
}