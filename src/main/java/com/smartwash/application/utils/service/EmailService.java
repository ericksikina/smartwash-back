package com.smartwash.application.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String remetente;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailTexto(String destinatario, String mensagem){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject("Pedido finalizado!");
            simpleMailMessage.setText(mensagem);
            mailSender.send(simpleMailMessage);
        } catch (Exception e){
            throw new IllegalArgumentException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}