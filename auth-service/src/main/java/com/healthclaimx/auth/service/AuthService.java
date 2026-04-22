package com.healthclaimx.auth.service;

import com.healthclaimx.auth.dto.LoginRequest;
import com.healthclaimx.auth.dto.LoginResponse;
import com.healthclaimx.auth.entity.User;
import com.healthclaimx.auth.repository.UserRepository;
import com.healthclaimx.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameAndZone(request.getUsername(), request.getZone())
            .orElseThrow(() -> new RuntimeException("User not found in zone: " + request.getZone()));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtService.generateToken(user);
        
        return new LoginResponse(token, user.getUsername(), user.getZone(), user.getId());
    }
    
    public User registerUser(User user) {
        if (user.getZone() == null || user.getZone().isEmpty()) {
            throw new RuntimeException("Zone must be specified for user registration");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User getUserByIdAndZone(Long userId, String zone) {
        return userRepository.findByIdAndZone(userId, zone)
            .orElseThrow(() -> new RuntimeException("User not found in zone: " + zone));
    }
}
