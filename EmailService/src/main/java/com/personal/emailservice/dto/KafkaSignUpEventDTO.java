package com.personal.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaSignUpEventDTO {
    private String to;
    private String from;
    private String subject;
    private String body;
}
