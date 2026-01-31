package org.example.uberprojectauthservice.Dto;

import lombok.*;
import org.example.uberprojectauthservice.Models.Passenger;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String password; //encrypted password
    private String phoneNumber;
    private LocalDateTime createdAt;

    public static PassengerResponseDto from(Passenger p){
        PassengerResponseDto result = PassengerResponseDto.builder()
                                     .id(p.getId())
                                     .createdAt(p.getCreatedAt())
                                     .email(p.getEmail())
                                     .password(p.getPassword())
                                     .phoneNumber(p.getPhoneNumber())
                                     .name(p.getName())
                                     .build();
        return result;
    }
}
