package com.hekimasist.backend.repository;

import java.util.List; // List importu eklendi
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hekimasist.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  // Username yerine tcNo ile arama yapıyoruz
  Optional<User> findByTcNo(String tcNo);

  // [YENİ] Belirli bir role sahip kullanıcıları getir (Örn: DOKTOR)
  List<User> findByRole(String role);
}