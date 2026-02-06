package com.hekimasist.backend.dto;

import com.hekimasist.backend.entity.User;
import lombok.Data;

@Data
public class DoctorDto {
  private Long id;
  private String tcNo;
  private String firstName;
  private String lastName;
  private String title;
  private String specialty;
  private String fullName; // "Dr. Ahmet Yılmaz" formatı için

  // Entity'den DTO'ya çeviren yardımcı metod
  public static DoctorDto fromEntity(User user) {
    DoctorDto dto = new DoctorDto();
    dto.setId(user.getId());
    dto.setTcNo(user.getTcNo());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setTitle(user.getTitle());
    dto.setSpecialty(user.getSpecialty());

    // Unvan varsa ekle, yoksa sadece ad soyad
    String prefix =
        (user.getTitle() != null && !user.getTitle().isEmpty()) ? user.getTitle() + " " : "";
    dto.setFullName(prefix + user.getFirstName() + " " + user.getLastName());

    return dto;
  }
}