package org.example.uberprojectauthservice.Controllers;

import org.example.uberprojectauthservice.Dto.PassengerResponseDto;
import org.example.uberprojectauthservice.Dto.PassengerSignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerResponseDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        return null;
    }
}
