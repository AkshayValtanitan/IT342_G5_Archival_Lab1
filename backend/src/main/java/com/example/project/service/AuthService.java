package com.example.project.service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Register ---------------------------------------------------------------------

    public String register(String username, String password) {
        // if (userRepository.existsByUsername(username)) {
        //     return "Username already taken";
        // }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "User registered successfully";
    }

    // Login ----------------------------------------------------------------------

    public String login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()) ?
                        "Login successful" :
                        "Incorrect password")
                .orElse("User not found");
    }
}
