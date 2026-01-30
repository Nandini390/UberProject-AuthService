package org.example.uberprojectauthservice.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
