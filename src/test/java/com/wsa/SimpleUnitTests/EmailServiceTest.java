package com.wsa.SimpleUnitTests;

import com.wsa.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendSimpleMail() {
        // Arrange
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "Test Email Body";

        // Act
        emailService.sendSimpleMail(to, subject, text);

        // Assert
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendTestMail() {
        // Act
        emailService.sendTestMail();

        // Assert
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
