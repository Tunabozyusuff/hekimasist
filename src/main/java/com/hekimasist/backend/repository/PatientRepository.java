package com.hekimasist.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hekimasist.backend.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

  boolean existsByTcNo(String tcNo);

  Optional<Patient> findByTcNo(String tcNo);

  // Arama fonksiyonu (Mevcut yapın korundu)
  List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrTcNoContaining(
      String firstName, String lastName, String tcNo);

  // --- YENİ METOD ---
  // BUGÜN kayıt olanların sayısını bulmak için (Protokol No hesabı için gerekli)
  long countByCreatedAtAfter(LocalDateTime startOfDay);
}