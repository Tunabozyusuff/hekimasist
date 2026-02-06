package com.hekimasist.backend.exception;

// [GÜNCELLEME] RuntimeException'dan türeterek uygulamanın durmasını engelliyoruz
public class BusinessException extends RuntimeException {

  // [YENİ] Nesnenin versiyonunu belirler, serileştirme hatalarını önlemek için standarttır
  private static final long serialVersionUID = 1L;

  // [DÜZELTME] Sadece mesaj alan constructor
  public BusinessException(String message) {
    super(message);
  }
}