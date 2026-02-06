package com.hekimasist.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Her randevu bir hastaya aittir.
  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @Column(nullable = false)
  private LocalDate appointmentDate;

  @Column(nullable = false)
  private LocalTime appointmentTime;

  private String doctorId; // dr1, dr2 gibi formdan gelen ID
  private String complaint; // Şikayet/Not

  private LocalDateTime createdAt = LocalDateTime.now();
}