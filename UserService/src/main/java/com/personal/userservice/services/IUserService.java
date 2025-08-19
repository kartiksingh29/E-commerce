package com.personal.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personal.userservice.exceptions.InvalidOrExpiredTokenException;
import com.personal.userservice.exceptions.InvalidPasswordException;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.exceptions.UserDoesNotExistException;
import com.personal.userservice.models.Token;
import com.personal.userservice.models.User;

public interface IUserService {
    public User signup(String name,String email,String password) throws UserAlreadyExistException, JsonProcessingException;
    public Token login(String email, String password) throws UserDoesNotExistException, InvalidPasswordException;
    public Token logout(String tokenValue) throws InvalidOrExpiredTokenException;
    public Token validateToken(String tokenValue) throws InvalidOrExpiredTokenException;
}
