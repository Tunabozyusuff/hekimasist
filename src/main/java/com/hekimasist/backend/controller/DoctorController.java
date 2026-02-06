package com.hekimasist.backend.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hekimasist.backend.dto.DoctorDto;
import com.hekimasist.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class DoctorController {

  private final UserRepository userRepository;

  public DoctorController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public ResponseEntity<List<DoctorDto>> getAllDoctors() {
    // Rolü "DOKTOR" olan kullanıcıları bul ve DTO'ya çevir
    List<DoctorDto> doctors = userRepository.findByRole("DOKTOR").stream()
        .map(DoctorDto::fromEntity).collect(Collectors.toList());

    return ResponseEntity.ok(doctors);
  }
}