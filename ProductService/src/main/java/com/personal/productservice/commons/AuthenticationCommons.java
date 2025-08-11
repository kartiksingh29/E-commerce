package com.personal.productservice.commons;

import com.personal.productservice.dto.ValidationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationCommons {

    private RestTemplate restTemplate;

    @Autowired
    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ValidationResponseDTO validateToken(String token) {

        ResponseEntity<ValidationResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:8181/users/"+token,
                ValidationResponseDTO.class
        );
        return responseEntity.getBody();
    }
}

// this
