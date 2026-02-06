package com.hekimasist.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tc_no", nullable = false, unique = true, length = 11)
  private String tcNo;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "specialty")
  private String specialty; // Uzmanlık (Dahiliye, KBB vb.)

  @Column(name = "title")
  private String title; // Unvan (Dr., Prof. Dr. vb.)

  // Frontend'de select box içinde "Dr. Ad Soyad" şeklinde göstermek için yardımcı metod
  public String getFullName() {
    return (title != null ? title + " " : "") + firstName + " " + lastName;
  }
}