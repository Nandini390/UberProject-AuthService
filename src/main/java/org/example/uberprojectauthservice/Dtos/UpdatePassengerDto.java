package org.example.uberprojectauthservice.Dtos;

import lombok.Data;

@Data
public class UpdatePassengerDto {
    private String name;
    private String password;
    private String phoneNumber;
    private Double rating;
}
