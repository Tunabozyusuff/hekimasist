package com.hekimasist.backend.dto;

public class LoginRequest {
  private String tcNo;
  private String password;

  // Getter ve Setterlar
  public String getTcNo() {
    return tcNo;
  }

  public void setTcNo(String tcNo) {
    this.tcNo = tcNo;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}