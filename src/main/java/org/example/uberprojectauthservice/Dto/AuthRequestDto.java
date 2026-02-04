package org.example.uberprojectauthservice.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String email;
    private String password;
}
