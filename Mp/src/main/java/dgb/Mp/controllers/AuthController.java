package com.example.auth.controller;


import dgb.Mp.user.Dtos.AuthResponse;
import dgb.Mp.user.Dtos.UserDtoLogin;
import dgb.Mp.user.Dtos.UserDtoToAdd;
import dgb.Mp.user.User;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private dgb.Mp.user.UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDtoToAdd userDtoToAdd) throws MessagingException {


       User registeredUser = userService.registerUser(userDtoToAdd);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDtoLogin user, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("username", user.getUsername());
        return ResponseEntity.ok(new AuthResponse("Login successful", user.getUsername()));
    }

    @GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return ResponseEntity.ok(new AuthResponse("Session active", username));
        }
        return ResponseEntity.status(401).body("No active session");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}

// Simple response class

