package com.example.notification.service;

import com.example.notification.dto.UserEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEventDto event) {
        log.info("Получено событие из Kafka: email={}, operationType={}", event.getEmail(), event.getOperationType());
        notificationService.sendNotification(event.getEmail(), event.getOperationType());
    }
}