package org.example.uberprojectauthservice.Dtos;

public record TokenResponse(String accessToken, String refreshToken, long expiresIn, String tokenType,
                            SignupResponseDto user){

    public static TokenResponse of(String accessToken, String refreshToken, long expiresIn, SignupResponseDto user){
        return new TokenResponse(accessToken,refreshToken,expiresIn,"Bearer",user);
    }
}
