package org.example.uberprojectauthservice.Dtos;

import lombok.*;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {
    private String id;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private String aadharCard;
    private String email;
    private DriverApprovalStatus driverApprovalStatus;
    private String denialReason;    // shown if DENIED
    private Double rating;
    private String activeCity;
    private Boolean isAvailable;
}
