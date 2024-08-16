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
        message.setFrom("13253348930@163.com");  // 发件人邮箱
        message.setTo("chenn16@cardiff.ac.uk");  // 收件人邮箱
        message.setSubject("测试邮件");
        message.setText("这是一封来自Spring Boot的测试邮件");

        mailSender.send(message);
        System.out.println("邮件发送成功");
    }
}
