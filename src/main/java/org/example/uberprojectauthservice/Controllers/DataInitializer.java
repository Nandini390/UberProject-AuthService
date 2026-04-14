package org.example.uberprojectauthservice.controllers;

import lombok.RequiredArgsConstructor;

import org.example.uberprojectauthservice.Repositories.UserRepository;
import org.example.uberprojectentityservice.Models.Provider;
import org.example.uberprojectentityservice.Models.Role;
import org.example.uberprojectentityservice.Models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.name}")
    private String adminName;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .provider(Provider.LOCAL)
                    .name(adminName)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created successfully");
        } else {
            System.out.println("Admin user already exists, skipping...");
        }
    }
}
