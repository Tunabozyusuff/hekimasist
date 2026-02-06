package com.hekimasist.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hekimasist.backend.entity.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
  // Önce URGENT, sonra CHRONIC, sonra NORMAL ve isim sırasına göre
  List<Complaint> findByIsActiveTrueOrderByPriorityAscNameAsc();
}