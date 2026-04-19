package org.example.uberprojectauthservice.Repositories;

import org.example.uberprojectentityservice.Models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Optional<Passenger> findByUserId(UUID userId);
}
