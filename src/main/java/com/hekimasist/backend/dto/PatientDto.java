package com.hekimasist.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.hekimasist.backend.enums.Priority;
import com.hekimasist.backend.enums.VisitStatus;
import lombok.Data;

@Data
public class PatientDto {
  private Long id;
  private String tcNo;
  private String firstName;
  private String lastName;
  private String fullName; // UI'da tek satırda göstermek için (Ad + Soyad)
  private String gender;
  private Integer age; // Backend'de hesaplanıp gidecek

  private String protocolNo; // 101, 102...
  private VisitStatus status; // Enum olarak gidecek
  private Priority priority; // Enum olarak gidecek

  private String complaint;
  private String bloodType;
  private String phoneNumber;
  private Boolean isFirstVisit;
  private List<String> tests;

  private LocalDateTime createdAt; // Kayıt saati
}