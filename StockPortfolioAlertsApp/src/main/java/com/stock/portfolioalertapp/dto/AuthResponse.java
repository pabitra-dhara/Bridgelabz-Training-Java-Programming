package com.stock.portfolioalertapp.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthResponse {
    private String token;
    private UUID userId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuthResponse() {}

    public AuthResponse(String token, UUID userId, String name, String email,
                        String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String getToken(){return token;} public void setToken(String v){token=v;}
    public UUID getUserId(){return userId;} public void setUserId(UUID v){userId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhoneNumber(){return phoneNumber;} public void setPhoneNumber(String v){phoneNumber=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    public LocalDateTime getUpdatedAt(){return updatedAt;} public void setUpdatedAt(LocalDateTime v){updatedAt=v;}
}
