package com.hekimasist.backend.enums;

public enum VisitStatus {
  WAITING, // Bekliyor (İlk kayıt anı)
  IN_PROGRESS, // Muayenede (Doktor "Çağır" dediğinde)
  COMPLETED, // Tamamlandı (Doktor işlemi bitirdiğinde)
  CANCELLED // İptal (Hasta gelmedi vb.)
}