package com.personal.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.userservice.dto.KafkaSignUpEventDTO;
import com.personal.userservice.exceptions.InvalidOrExpiredTokenException;
import com.personal.userservice.exceptions.InvalidPasswordException;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.exceptions.UserDoesNotExistException;
import com.personal.userservice.models.Token;
import com.personal.userservice.models.User;
import com.personal.userservice.repositories.TokenRepository;
import com.personal.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenRepository tokenRepository;

    // spring automatically creates bean for KafkaTemplate so no need
    // to configure unlike for Redis
    private KafkaTemplate<String, String> kafkaTemplate ;

    private ObjectMapper objectMapper ;


    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository,
                       KafkaTemplate<String, String> kafkaTemplate,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signup(String name, String email, String password) throws UserAlreadyExistException, JsonProcessingException {
        Optional<User> userOptional =
                userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("The user already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        //after saving new user object to db, lets put an event to Kafka queue
        KafkaSignUpEventDTO kafkaSignUpEventDTO = new KafkaSignUpEventDTO();
        kafkaSignUpEventDTO.setTo(savedUser.getEmail());
        kafkaSignUpEventDTO.setFrom("singh.kartik79@gmail.com");
        kafkaSignUpEventDTO.setSubject("Successful SignUp");
        kafkaSignUpEventDTO.setBody("We are happy to register you as a user on Kartik Singh's e-commerce platform");

        // adding the event to Kafka queue
        kafkaTemplate.send("signUpEvent",
                objectMapper.writeValueAsString(kafkaSignUpEventDTO));

        //return the successfully registered user
        return savedUser;
    }

    @Override
    public Token login(String email, String password) throws UserDoesNotExistException, InvalidPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException("This user is invalid! Please sign up first.");
        }
        // now if user is valid, then password needs to be validated
        User savedUser = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,savedUser.getHashedPassword())){
            throw new InvalidPasswordException("The password is invalid.");
        }
        // now if password is successfully authenticated, then generate token
        Token token = new Token();
        token.setUser(savedUser);

        // setting expiry of token to current date + 30 days
        token.setExpiryDate(Date.from(
                LocalDateTime.now()
                        .plusDays(30)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        ));

        // adding a random token value for now using apache-commons-lang library
        String value = RandomStringUtils.randomAlphanumeric(120);
        token.setValue(value);
        return tokenRepository.save(token);
    }

    @Override
    public Token logout(String tokenValue) throws InvalidOrExpiredTokenException {
        // if token was JWT token, then it is self validating, ie no database call
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndExpiryDateAfterAndDeleted(tokenValue, new Date(), false);
        if(tokenOptional.isEmpty()){
            throw new InvalidOrExpiredTokenException("The given token is either invalid or expired!");
        }
        // if token provided is a valid one, then we need to delete it
        Token token = tokenOptional.get();
        token.setDeleted(true);
        return tokenRepository.save(token);
    }

    @Override
    public Token validateToken(String tokenValue) throws InvalidOrExpiredTokenException {
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndExpiryDateAfterAndDeleted(tokenValue, new Date(), false);
        if(tokenOptional.isEmpty()){
            throw new InvalidOrExpiredTokenException("The given token is either invalid" +
                    " or expired!");
        }
        return tokenOptional.get();
    }
}
