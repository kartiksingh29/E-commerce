package com.personal.userservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
                try {
                    requests.anyRequest().permitAll()
                            .and().cors().disable()
                            .csrf().disable();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
        });
        return http.build();
    }
}

// this class is to disable the security for API testing via postman
//after adding spring-boot-starter-security in pom.xml all API's become secure.
// you need a user and password( printed on console ) to hit an endpoint
