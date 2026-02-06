package com.hekimasist.backend.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hekimasist.backend.dto.CreatePatientRequest;
import com.hekimasist.backend.dto.PatientDto;
import com.hekimasist.backend.entity.Patient;
import com.hekimasist.backend.enums.Priority;
import com.hekimasist.backend.enums.VisitStatus;
import com.hekimasist.backend.exception.BusinessException;
import com.hekimasist.backend.repository.PatientRepository;

@Service
public class PatientService {

  private final PatientRepository patientRepository;

  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  // --- YARDIMCI METOD: Entity -> DTO Dönüşümü ---
  private PatientDto mapToDto(Patient p) {
    PatientDto dto = new PatientDto();
    dto.setId(p.getId());
    dto.setTcNo(p.getTcNo());
    dto.setFirstName(p.getFirstName());
    dto.setLastName(p.getLastName());
    dto.setFullName(p.getFirstName() + " " + p.getLastName());
    dto.setGender(p.getGender());
    dto.setProtocolNo(p.getProtocolNo());
    dto.setStatus(p.getStatus());
    dto.setPriority(p.getPriority());
    dto.setComplaint(p.getComplaint());
    dto.setBloodType(p.getBloodType());
    dto.setPhoneNumber(p.getPhoneNumber());
    dto.setIsFirstVisit(p.getIsFirstVisit());
    dto.setTests(p.getTests() != null ? p.getTests() : new ArrayList<>()); // Null check
    dto.setCreatedAt(p.getCreatedAt());

    // Otomatik Yaş Hesabı
    if (p.getBirthDate() != null) {
      dto.setAge(Period.between(p.getBirthDate(), LocalDate.now()).getYears());
    }
    return dto;
    }

  // Tüm hastaları getir
  public List<PatientDto> getAllPatients() {
    return patientRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

  // Yeni hasta oluştur
  @Transactional
  public PatientDto createPatient(CreatePatientRequest request) {
    // 1. Validasyon: TC No Kontrolü
    if (patientRepository.existsByTcNo(request.getTcNo())) {
      throw new BusinessException("Bu TC numarası sistemde zaten kayıtlı!");
    }

    // 2. Entity Oluşturma
    Patient patient = new Patient();
    patient.setTcNo(request.getTcNo());
    patient.setFirstName(request.getFirstName());
    patient.setLastName(request.getLastName());
    patient.setGender(request.getGender());
    patient.setBirthDate(request.getBirthDate());

    // Detay Alanlar
    patient.setComplaint(request.getComplaint());
    patient.setBloodType(request.getBloodType());
    patient.setPhoneNumber(request.getPhoneNumber());
    patient.setIsFirstVisit(request.getIsFirstVisit());
    patient.setTests(request.getTests() != null ? request.getTests() : new ArrayList<>());

    // Öncelik ve Statü Varsayılanları
    patient.setPriority(request.getPriority() != null ? request.getPriority() : Priority.NORMAL);
    patient.setStatus(VisitStatus.WAITING);

    // 3. İŞ MANTIĞI: Günlük Protokol No (101, 102...)
    long todayCount = patientRepository.countByCreatedAtAfter(LocalDate.now().atStartOfDay());
    patient.setProtocolNo(String.valueOf(100 + todayCount + 1));

    return mapToDto(patientRepository.save(patient));
  }

  // Arama Fonksiyonu
  public List<PatientDto> searchPatients(String query) {
    return patientRepository
        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrTcNoContaining(query,
            query, query)
        .stream().map(this::mapToDto).collect(Collectors.toList());
  }

  // --- KRİTİK METOD: Çağır Butonu ve Statü Değişikliği ---
  @Transactional
  public PatientDto updateStatus(Long id, VisitStatus newStatus) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new BusinessException("Hasta bulunamadı ID: " + id));

    patient.setStatus(newStatus);
    return mapToDto(patientRepository.save(patient));
    }

  // Hasta Bilgilerini Güncelleme
  @Transactional
  public PatientDto updatePatient(Long id, CreatePatientRequest newPatientData) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new BusinessException("Hasta bulunamadı ID: " + id));

    // TC değişiyorsa ve yeni TC başkasında varsa hata fırlat
    if (!patient.getTcNo().equals(newPatientData.getTcNo())
        && patientRepository.existsByTcNo(newPatientData.getTcNo())) {
      throw new BusinessException("Güncellenmek istenen TC zaten başka bir hastaya ait!");
    }

    patient.setTcNo(newPatientData.getTcNo());
    patient.setFirstName(newPatientData.getFirstName());
    patient.setLastName(newPatientData.getLastName());
    patient.setGender(newPatientData.getGender());
    patient.setBirthDate(newPatientData.getBirthDate());
    patient.setComplaint(newPatientData.getComplaint());
    patient.setPhoneNumber(newPatientData.getPhoneNumber());
    patient.setPriority(newPatientData.getPriority());

    if (newPatientData.getTests() != null) {
      patient.setTests(newPatientData.getTests());
    }

    return mapToDto(patientRepository.save(patient));
  }

  // Silme İşlemi
  @Transactional
  public void deletePatient(Long id) {
    if (!patientRepository.existsById(id)) {
      throw new BusinessException("Silinmek istenen hasta bulunamadı ID: " + id);
    }
    patientRepository.deleteById(id);
    }
}