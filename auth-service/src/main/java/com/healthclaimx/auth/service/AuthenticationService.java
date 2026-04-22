package com.healthclaimx.auth.service;
import com.healthclaimx.auth.dto.LoginRequest;
import com.healthclaimx.auth.dto.LoginResponse;
import com.healthclaimx.auth.entity.User;
import com.healthclaimx.auth.entity.Zone;
import com.healthclaimx.auth.repository.UserRepository;
import com.healthclaimx.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.autentication.*;
import com.healthclaimx.auth.security.JwtUtils;
@Service
@RequiredArgsConstructor
public class AuthenticationService
{ private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;;
  private final JwtUtils jwtUtils;
  
  public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtils = jwtUtils;
  }

  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (AuthenticationException e) {
      throw new RuntimeException("Invalid username or password");
    }
    
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return jwtUtils.generateToken(userDetails);
  }
} 