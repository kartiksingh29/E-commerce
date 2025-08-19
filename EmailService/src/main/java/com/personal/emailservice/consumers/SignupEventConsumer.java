package com.personal.emailservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.emailservice.dto.KafkaSignUpEventDTO;
import com.personal.emailservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SignupEventConsumer {

    private ObjectMapper objectMapper;

    @Autowired
    public SignupEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics={"signUpEvent"},
            groupId = "signUpEventConsumerGroup"
    )
    public void handleSignUpEvent(String message) throws JsonProcessingException {
        // this String message variable contains the event details sent by Kafka producer
        KafkaSignUpEventDTO kafkaSignUpEventDTO = objectMapper.readValue(
                message, KafkaSignUpEventDTO.class);

        String to = kafkaSignUpEventDTO.getTo();
        final String from = kafkaSignUpEventDTO.getFrom();
        String subject = kafkaSignUpEventDTO.getSubject();
        String body = kafkaSignUpEventDTO.getBody();
        // created via Google account manager -> security -> app passwords
        final String appPassword = "rygjbyfztylcgxrn";

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS


        // Create Authenticator for Gmail login
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Use your Gmail username and App Password (no spaces)
                return new PasswordAuthentication(from, appPassword);
            }
        };

        // Create a Javax Mail session
        Session session = Session.getInstance(props, auth);

        // Send the email
        EmailUtil.sendEmail(session, to, subject, body);
    }
}
