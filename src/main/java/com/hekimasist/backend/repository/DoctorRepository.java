package com.hekimasist.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hekimasist.backend.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
  Optional<Doctor> findByTcNo(String tcNo);
}