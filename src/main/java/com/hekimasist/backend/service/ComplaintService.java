package com.hekimasist.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.hekimasist.backend.dto.ComplaintResponse;
import com.hekimasist.backend.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

  private final ComplaintRepository complaintRepository;

  public List<ComplaintResponse> getAllActiveComplaints() {
    return complaintRepository.findByIsActiveTrueOrderByPriorityAscNameAsc().stream()
        .map(complaint -> ComplaintResponse.builder().id(complaint.getId().toString()) // Frontend
                                                                                       // string
                                                                                       // bekliyor
            .label(complaint.getName()) // Frontend 'label' olarak kullanacak
            .priority(complaint.getPriority()).build())
        .collect(Collectors.toList());
  }
}