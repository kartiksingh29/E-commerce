package com.personal.userservice.controllers;

import com.personal.userservice.dto.*;
import com.personal.userservice.exceptions.InvalidOrExpiredTokenException;
import com.personal.userservice.exceptions.InvalidPasswordException;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.exceptions.UserDoesNotExistException;
import com.personal.userservice.models.Token;
import com.personal.userservice.models.User;
import com.personal.userservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDTO signupRequestDTO) throws UserAlreadyExistException {
        User user = userService.signup(signupRequestDTO.getName()
                ,signupRequestDTO.getEmail(), signupRequestDTO.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws InvalidPasswordException, UserDoesNotExistException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        Token token = userService.login(loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword());
        loginResponseDTO.setTokenValue(token.getValue());
        loginResponseDTO.setMessage("Successfully logged in");
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) throws InvalidOrExpiredTokenException {
        Token token = userService.logout(logoutRequestDTO.getToken());
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(
                token.isDeleted()?HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @GetMapping("/validate/{tokenValue}")
    public ResponseEntity<ValidationResponseDTO> validateToken(@PathVariable(name="tokenValue") String tokenValue) throws InvalidOrExpiredTokenException {
        Token token = userService.validateToken(tokenValue);
        ValidationResponseDTO validationResponseDTO = ValidationResponseDTO.from(token.getUser());
        return ResponseEntity.ok().body(validationResponseDTO);
    }
}
