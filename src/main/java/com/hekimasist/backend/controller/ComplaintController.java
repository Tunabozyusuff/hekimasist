package com.hekimasist.backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hekimasist.backend.dto.ComplaintResponse;
import com.hekimasist.backend.service.ComplaintService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

  private final ComplaintService complaintService;

  @GetMapping
  public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
    return ResponseEntity.ok(complaintService.getAllActiveComplaints());
  }
}