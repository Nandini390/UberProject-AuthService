package org.example.uberprojectauthservice.Controllers;

import lombok.Getter;
import org.example.uberprojectauthservice.Dto.PassengerResponseDto;
import org.example.uberprojectauthservice.Dto.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerResponseDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerResponseDto response= authService.SignupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/signIn/passenger")
    public ResponseEntity<?> signIn(){
        return new ResponseEntity<>(10, HttpStatus.CREATED);
    }
}
