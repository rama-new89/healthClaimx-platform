package com.healthclaimx.auth.controller;

import com.healthclaimx.auth.dto.LoginRequest;
import com.healthclaimx.auth.dto.LoginResponse;
import com.healthclaimx.auth.dto.RegisterRequest;
import com.healthclaimx.auth.dto.ErrorResponse;
import com.healthclaimx.auth.entity.User;
import com.healthclaimx.auth.service.AuthService;
import com.healthclaimx.auth.security.JwtService;
import com.healthclaimx.auth.config.ZoneConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Validate input
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Username is required", "Bad Request", LocalDateTime.now()));
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Password is required", "Bad Request", LocalDateTime.now()));
            }
            if (request.getZone() == null || request.getZone().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Zone is required", "Bad Request", LocalDateTime.now()));
            }
            
            // Validate zone
            if (!ZoneConfig.isValidZone(request.getZone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid zone: " + request.getZone(), "Bad Request", LocalDateTime.now()));
            }
            
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, e.getMessage(), "Unauthorized", LocalDateTime.now()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validate input
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Username is required", "Bad Request", LocalDateTime.now()));
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Password is required", "Bad Request", LocalDateTime.now()));
            }
            if (request.getZone() == null || request.getZone().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Zone is required", "Bad Request", LocalDateTime.now()));
            }
            
            // Validate zone
            if (!ZoneConfig.isValidZone(request.getZone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid zone: " + request.getZone(), "Bad Request", LocalDateTime.now()));
            }
            
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setZone(request.getZone());
            
            User registeredUser = authService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, e.getMessage(), "Bad Request", LocalDateTime.now()));
        }
    }
    
    @GetMapping("/user/{userId}/{zone}")
    public ResponseEntity<?> getUser(@PathVariable Long userId, @PathVariable String zone) {
        try {
            if (userId == null || userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid user ID", "Bad Request", LocalDateTime.now()));
            }
            if (zone == null || zone.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Zone is required", "Bad Request", LocalDateTime.now()));
            }
            
            if (!ZoneConfig.isValidZone(zone)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid zone: " + zone, "Bad Request", LocalDateTime.now()));
            }
            
            User user = authService.getUserByIdAndZone(userId, zone);
            return ResponseEntity.ok(user);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, e.getMessage(), "Not Found", LocalDateTime.now()));
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid token format", "Bad Request", LocalDateTime.now()));
            }
            
            String extractedToken = token.substring(7);
            if (jwtService.validateToken(extractedToken)) {
                String username = jwtService.extractUsername(extractedToken);
                String zone = jwtService.extractZone(extractedToken);
                return ResponseEntity.ok(new LoginResponse(extractedToken, username, zone, null));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(401, "Token is invalid or expired", "Unauthorized", LocalDateTime.now()));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, "Invalid token: " + e.getMessage(), "Unauthorized", LocalDateTime.now()));
        }
    }
}
