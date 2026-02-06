package com.hekimasist.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hekimasist.backend.dto.CreatePatientRequest;
import com.hekimasist.backend.dto.PatientDto;
import com.hekimasist.backend.enums.VisitStatus;
import com.hekimasist.backend.service.PatientService;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}) // Frontend portlarına
                                                                           // izin ver
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  // Tüm hastaları getir (DTO döner)
  @GetMapping
  public List<PatientDto> getAllPatients() {
    return patientService.getAllPatients();
  }

  // Yeni hasta ekle (CreateRequest alır, DTO döner)
  @PostMapping
  public PatientDto createPatient(@RequestBody CreatePatientRequest request) {
    return patientService.createPatient(request);
  }

  // Arama yap
  @GetMapping("/search")
  public List<PatientDto> searchPatients(@RequestParam String query) {
    return patientService.searchPatients(query);
  }

  // Sadece Statü Değiştirme (Örn: ÇAĞIR butonu için)
  // PATCH /api/patients/1/status?status=IN_PROGRESS
  @PatchMapping("/{id}/status")
  public PatientDto updateStatus(@PathVariable Long id, @RequestParam VisitStatus status) {
    return patientService.updateStatus(id, status);
  }

  // Hasta bilgilerini güncelle
  @PutMapping("/{id}")
  public PatientDto updatePatient(@PathVariable Long id,
      @RequestBody CreatePatientRequest request) {
    return patientService.updatePatient(id, request);
  }

  // Sil
  @DeleteMapping("/{id}")
  public void deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
  }
}