package com.example.user.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserEvent(String email, String operationType) {
        Map<String, String> event = Map.of(
                "email", email,
                "operationType", operationType
        );
        kafkaTemplate.send("user-events", event);
    }
}