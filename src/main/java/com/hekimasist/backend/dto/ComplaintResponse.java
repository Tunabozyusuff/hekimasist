package com.hekimasist.backend.dto;

import com.hekimasist.backend.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {
  private String id; // Frontend string beklediği için mapping'de toString yapacağız
  private String label; // Frontend 'label' olarak bekliyor (mapping kolaylığı)
  private Priority priority;
}