package org.example.uberprojectauthservice.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {
    private String name;
    private String email;
    private String password;
}
