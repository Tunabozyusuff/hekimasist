package com.hekimasist.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hekimasist.backend.entity.Appointment;
import com.hekimasist.backend.entity.Patient;
import com.hekimasist.backend.repository.AppointmentRepository;
import com.hekimasist.backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final PatientRepository patientRepository;

  @Transactional
  public Appointment createAppointment(String tcNo, String fullName, String phone, String doctorId,
      String complaint, LocalDate date, LocalTime time) {

    // 1. Çakışma Kontrolü
    if (appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(doctorId, date,
        time)) {
      throw new RuntimeException("Seçilen saat dilimi bu doktor için zaten dolu.");
    }

    // 2. Akıllı Hasta Yönetimi
    Patient patient = patientRepository.findByTcNo(tcNo).orElseGet(() -> {
      // Veritabanında bu TC yoksa yeni bir hasta kartı aç:
      Patient newPatient = new Patient();
      newPatient.setTcNo(tcNo);

      // Ad Soyad ayırma mantığı
      String[] names = fullName.split(" ");
      newPatient.setFirstName(names[0]);
      newPatient.setLastName(names.length > 1 ? names[names.length - 1] : "");

      newPatient.setPhoneNumber(phone);
      newPatient.setIsFirstVisit(true); // Randevu ile ilk kez geldi
      return patientRepository.save(newPatient);
    });

    // 3. Randevuyu Kaydet
    Appointment appointment = new Appointment();
    appointment.setPatient(patient);
    appointment.setDoctorId(doctorId);
    appointment.setAppointmentDate(date);
    appointment.setAppointmentTime(time);
    appointment.setComplaint(complaint);

    return appointmentRepository.save(appointment);
  }
}