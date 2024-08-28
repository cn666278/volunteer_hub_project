package com.wsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class for handling email sending operations.
 * This service uses Spring's JavaMailSender to send simple text emails.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender; // JavaMailSender instance for sending emails

    /**
     * Sends a simple email with the specified recipient, subject, and text content.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param text The content of the email.
     */
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // Set the recipient's email address
        message.setSubject(subject); // Set the email subject
        message.setText(text); // Set the email body/content
        mailSender.send(message); // Send the email
    }

    /**
     * Sends a test email to verify the email sending functionality.
     * This method is intended for testing purposes and sends a predefined email.
     */
    public void sendTestMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13253348930@163.com"); // Set the sender's email address
        message.setTo("chenn16@cardiff.ac.uk"); // Set the recipient's email address
        message.setSubject("test email"); // Set the email subject
        message.setText("This is a test email from Spring Boot"); // Set the email body/content

        mailSender.send(message); // Send the test email
        System.out.println("Email sent successfully"); // Log to indicate successful sending
    }
}
