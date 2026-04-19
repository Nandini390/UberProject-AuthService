package org.example.uberprojectauthservice.Dtos;

import lombok.Data;

@Data
public class UpdateDriverDto {
    private String name;
    private String password;
    private String licenseNumber;
    private String aadharCard;
    private String phoneNumber;
    private Boolean isAvailable;
    private Double rating;
    private String activeCity;
}
