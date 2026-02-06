package com.hekimasist.backend.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hekimasist.backend.dto.AuthResponse;
import com.hekimasist.backend.dto.LoginRequest;
import com.hekimasist.backend.entity.User;
import com.hekimasist.backend.repository.UserRepository;
import com.hekimasist.backend.service.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public User register(@Valid @RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest loginRequest) {
    // 1. Kimlik doğrulama (tcNo ve password ile)
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getTcNo(), loginRequest.getPassword()));

    // 2. Kullanıcı bilgilerini veritabanından çek (İsim ve soyisim için)
    User user = userRepository.findByTcNo(loginRequest.getTcNo())
        .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));

    // 3. Token üret
    // Not: loadUserByUsername metodun tcNo döndürdüğü için jwtService sorunsuz çalışacaktır
    String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
        user.getTcNo(), user.getPassword(), java.util.Collections.emptyList()));

    // 4. Frontend'in beklediği toplu yanıtı dön
    return new AuthResponse(token, user.getFirstName(), user.getLastName(), user.getRole());
    }
}