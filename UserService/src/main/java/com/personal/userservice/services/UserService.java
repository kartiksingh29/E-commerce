package com.personal.userservice.services;

import com.personal.userservice.dto.SignupRequestDTO;
import com.personal.userservice.exceptions.UserAlreadyExistException;
import com.personal.userservice.models.User;
import com.personal.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signup(String name, String email, String password) throws UserAlreadyExistException {
        Optional<User> userOptional =
                userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("The user already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public boolean logout(String token) {
        return false;
    }
}
