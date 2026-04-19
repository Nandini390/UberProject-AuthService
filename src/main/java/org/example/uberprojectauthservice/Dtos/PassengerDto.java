package org.example.uberprojectauthservice.Dtos;

import lombok.Data;

@Data
public class PassengerDto {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Double rating;
}
