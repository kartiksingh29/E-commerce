package com.personal.userservice.controllers;

import com.personal.userservice.dto.LoginRequestDTO;
import com.personal.userservice.dto.LogoutRequestDTO;
import com.personal.userservice.dto.SignupRequestDTO;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.models.Token;
import com.personal.userservice.models.User;
import com.personal.userservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDTO signupRequestDTO) throws UserAlreadyExistException {
        String name = signupRequestDTO.getName();
        String email = signupRequestDTO.getEmail();
        String password = signupRequestDTO.getPassword();
        User user = userService.signup(name, email, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public Token logIn(LoginRequestDTO loginRequestDTO){
        return null;
    }

    @PostMapping("/logout")
    public boolean logOut(LogoutRequestDTO loginRequestDTO){
        return false;
    }

}
