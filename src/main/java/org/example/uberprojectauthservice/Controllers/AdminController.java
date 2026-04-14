package org.example.uberprojectauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.DenyRequestDto;
import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Services.AdminService;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    // Get all drivers by status
    @GetMapping("/drivers")
    public ResponseEntity<List<DriverDto>> getDriversByStatus(@RequestParam(required = false, defaultValue = "PENDING") DriverApprovalStatus status) {
        return ResponseEntity.ok(adminService.getDriversByStatus(status));
    }

    // Get single driver details
    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable String driverId) {
        return ResponseEntity.ok(adminService.getDriverById(driverId));
    }

    // Approve a driver
    @PatchMapping("/drivers/{driverId}/approve")
    public ResponseEntity<DriverDto> approveDriver(@PathVariable String driverId) {
        return ResponseEntity.ok(adminService.updateDriverStatus(driverId, DriverApprovalStatus.APPROVED));
    }

    // Deny a driver
    @PatchMapping("/drivers/{driverId}/deny")
    public ResponseEntity<DriverDto> denyDriver(
            @PathVariable String driverId,
            @RequestBody DenyRequestDto denyRequest) {  // admin can provide reason
        return ResponseEntity.ok(adminService.denyDriver(driverId, denyRequest.getReason()));
    }
}
