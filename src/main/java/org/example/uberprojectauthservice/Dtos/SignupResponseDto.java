package org.example.uberprojectauthservice.Dtos;

import lombok.*;
import org.example.uberprojectentityservice.Models.Role;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SignupResponseDto {
    private String message;
    private String name;
    private String userId;
    private String role;
}