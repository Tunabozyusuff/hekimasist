package com.hekimasist.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.hekimasist.backend.enums.Priority;
import com.hekimasist.backend.enums.VisitStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "TC No boş olamaz")
  @Size(min = 11, max = 11, message = "TC No 11 haneli olmalıdır")
  @Pattern(regexp = "^[0-9]+$", message = "TC No sadece rakamlardan oluşmalıdır")
  @Column(name = "tc_no", nullable = false, unique = true)
  private String tcNo;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String gender; // E, K

  @Column(name = "birth_date")
  private LocalDate birthDate;

  // --- YENİ EKLENEN ALANLAR ---

  private String protocolNo; // Günlük sıra no: 101, 102...

  @Enumerated(EnumType.STRING)
  private VisitStatus status = VisitStatus.WAITING; // Default: BEKLİYOR

  @Enumerated(EnumType.STRING)
  private Priority priority = Priority.NORMAL; // Default: NORMAL

  private String complaint; // Şikayet
  private String bloodType; // Kan Grubu
  private String phoneNumber;
  private Boolean isFirstVisit;

  // Tetkikler listesini veritabanında "patient_tests" adlı ayrı bir tabloda tutar
  @ElementCollection
  @CollectionTable(name = "patient_tests", joinColumns = @JoinColumn(name = "patient_id"))
  @Column(name = "test_name")
  private List<String> tests;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    if (status == null)
      status = VisitStatus.WAITING;
    if (priority == null)
      priority = Priority.NORMAL;
  }
}