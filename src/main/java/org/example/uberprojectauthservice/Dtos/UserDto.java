package org.example.uberprojectauthservice.Dtos;

import jakarta.persistence.EntityListeners;
import lombok.*;
import org.example.uberprojectentityservice.Models.Provider;
import org.example.uberprojectentityservice.Models.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserDto {
    private UUID id;
    private String email;
    private String name;
    private Provider provider=Provider.LOCAL;
    private Role role;
}
