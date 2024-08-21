package com.wsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public void sendTestMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13253348930@163.com");
        message.setTo("chenn16@cardiff.ac.uk");
        message.setSubject("test email");
        message.setText("This is a test email from Spring Boot");

        mailSender.send(message);
        System.out.println("Email sent successfully");
    }
}
