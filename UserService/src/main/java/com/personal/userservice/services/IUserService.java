package com.personal.userservice.services;

import com.personal.userservice.dto.SignupRequestDTO;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.models.User;

public interface IUserService {
    public User signup(String name,String email,String password) throws UserAlreadyExistException;
    public User login(String email, String password);
    public boolean logout(String token);
}
