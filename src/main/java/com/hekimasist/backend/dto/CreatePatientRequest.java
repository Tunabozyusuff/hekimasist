package com.hekimasist.backend.dto;

import java.time.LocalDate;
import java.util.List;
import com.hekimasist.backend.enums.Priority;
import lombok.Data;

@Data
public class CreatePatientRequest {
  private String tcNo;
  private String firstName;
  private String lastName;
  private String gender; // "E" veya "K"
  private LocalDate birthDate;

  // Yeni eklenen alanlar
  private String complaint; // Şikayet
  private String bloodType; // Kan Grubu
  private String phoneNumber;
  private Boolean isFirstVisit;
  private Priority priority; // "NORMAL", "URGENT" vb.
  private List<String> tests; // ["Hemogram", "EKG"]
}