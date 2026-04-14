package org.example.uberprojectauthservice.Repositories;

import org.example.uberprojectentityservice.Models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
