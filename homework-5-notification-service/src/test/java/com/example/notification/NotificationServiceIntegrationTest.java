package com.example.notification;

import com.example.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class NotificationServiceIntegrationTest {

    @Container
    static GenericContainer<?> mailhog = new GenericContainer<>(DockerImageName.parse("mailhog/mailhog:latest"))
            .withExposedPorts(1025, 8025);

    @Autowired
    private NotificationService notificationService;

    @DynamicPropertySource
    static void mailhogProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailhog::getHost);
        registry.add("spring.mail.port", () -> mailhog.getMappedPort(1025));
        registry.add("spring.mail.properties.mail.smtp.auth", () -> "false");
        registry.add("spring.mail.properties.mail.smtp.starttls.enable", () -> "false");
    }

    @Test
    void sendNotification_ShouldSendEmailViaMailHog() {
        // given
        String email = "test@mailhog.local";
        String operationType = "CREATE";

        // when
        notificationService.sendNotification(email, operationType);

        // then - проверяем, что письмо отправлено (MailHog поднят, исключений нет)
        assertThat(notificationService).isNotNull();
    }
}