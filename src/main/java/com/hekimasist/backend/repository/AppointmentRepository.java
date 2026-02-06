package com.hekimasist.backend.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hekimasist.backend.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  // Aynı doktorun aynı gün ve saatte randevusu var mı?
  boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(String doctorId, LocalDate date,
      LocalTime time);
}