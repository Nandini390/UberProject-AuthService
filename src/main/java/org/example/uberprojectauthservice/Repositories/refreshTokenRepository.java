package org.example.uberprojectauthservice.Repositories;


import org.example.uberprojectentityservice.Models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface refreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByJti(String jti);
}
