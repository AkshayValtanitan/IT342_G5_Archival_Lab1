package com.example.project.controller;

// import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public Object me(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "You are not logged in";
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
