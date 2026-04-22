package com.healthclaimx.auth.controller;

import com.healthclaimx.auth.dto.LoginRequest;
import com.healthclaimx.auth.dto.LoginResponse;
import com.healthclaimx.auth.entity.User;
import com.healthclaimx.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = authService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
    
    @GetMapping("/user/{userId}/{zone}")
    public ResponseEntity<User> getUser(@PathVariable Long userId, @PathVariable String zone) {
        User user = authService.getUserByIdAndZone(userId, zone);
        return ResponseEntity.ok(user);
    }
}
