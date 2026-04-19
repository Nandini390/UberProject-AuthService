package org.example.uberprojectauthservice.Repositories;

import org.example.uberprojectentityservice.Models.Driver;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    List<Driver> findByDriverApprovalStatus(DriverApprovalStatus status);
    Optional<Driver> findByUserId(UUID UserId);
}
