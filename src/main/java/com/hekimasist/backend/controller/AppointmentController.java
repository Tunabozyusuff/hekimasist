package com.hekimasist.backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hekimasist.backend.service.AppointmentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

  private final AppointmentService appointmentService;

  @PostMapping("/create")
  public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
    try {
      var appointment = appointmentService.createAppointment(request.getTcNo(),
          request.getFullName(), request.getPhone(), request.getDoctor(), request.getComplaint(),
          request.getDate(), LocalTime.parse(request.getTime()));
      return ResponseEntity.ok(appointment);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}


@Data
class AppointmentRequest {
  private String tcNo;
  private String fullName;
  private String phone;
  private String doctor;
  private String complaint;
  private LocalDate date;
  private String time;
}
