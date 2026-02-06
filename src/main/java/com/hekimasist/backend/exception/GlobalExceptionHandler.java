package com.hekimasist.backend.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Tüm controller katmanındaki hataları merkezi olarak yakalar
public class GlobalExceptionHandler {

  // [GÜNCELLEME] BusinessException yakalandığında JSON objesi dönecek şekilde düzenlendi
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    // [EKLEME] Frontend'in kolayca okuyabilmesi için "message" anahtarına hata metnini koyuyoruz
    errorResponse.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Hatası
        .body(errorResponse);
  }

  // [GÜNCELLEME] Genel sistem hataları için de JSON formatı
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", "Sistemsel bir hata oluştu: " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Hatası
        .body(errorResponse);
  }
}