package org.example.uberprojectauthservice.Dtos;

import lombok.*;
import org.example.uberprojectentityservice.Models.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    private String email;
    private String password;
    private Role role;
    private String name;

    private String phoneNumber;
    private String licenseNumber;
    private String aadharCard;
    private String activeCity;
}