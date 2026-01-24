package com.bsuir_finder.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void send(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            //TODO: add spring variables without hard-code
            message.setFrom("atm54423@gmail.com");
            message.setSubject("Confirm your email");
            String messageBody = """
                
                Thank you for registration. Please confirm your email.
                
                http://localhost:8080/register/confirmToken?token=%s
                
                """.formatted(token);
            message.setText(messageBody);
            mailSender.send(message);
        } catch(Exception e) {
            e.printStackTrace();
            throw new IllegalIdentifierException("Failed to send email");
        }
    }
}