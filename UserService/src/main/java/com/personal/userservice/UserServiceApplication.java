package com.personal.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}

// FINAL POINTS TO IMPLEMENT IN OAuth SERVER

// 1) I want to use my own database to maintain users

// 2) Add custom data to the JWT tokens.

// 3) I want product-service to use this OAuth Server of mine for authentication.