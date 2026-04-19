package org.example.uberprojectauthservice.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Repositories.UserRepository;
import org.example.uberprojectauthservice.Repositories.refreshTokenRepository;
import org.example.uberprojectentityservice.Models.Provider;
import org.example.uberprojectentityservice.Models.RefreshToken;
import org.example.uberprojectentityservice.Models.Role;
import org.example.uberprojectentityservice.Models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final CookieService cookieService;
    private final refreshTokenRepository refreshTokenRepository;

    @Value("${app.auth.frontend.success-redirect}")
    private String frontendSuccessUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
         logger.info("success authentication");
         logger.info(authentication.toString());

        OAuth2User OAuth2User=(OAuth2User) authentication.getPrincipal();
        String registrationId="unknown";
        if(authentication instanceof OAuth2AuthenticationToken token){
            registrationId=token.getAuthorizedClientRegistrationId();
        }
        logger.info("registrationId: "+registrationId);
        logger.info("user: "+OAuth2User.getAttributes().toString());

        User user;
        boolean isNewUser = false;
        switch (registrationId){
            case "google"-> {
                String googleId = OAuth2User.getAttributes().getOrDefault("sub", "").toString();
                String email = OAuth2User.getAttributes().getOrDefault("email", "").toString();
                String name = OAuth2User.getAttributes().getOrDefault("name", "").toString();
                // Check if user already exists
                Optional<User> existingUser = userRepository.findByEmail(email);

                if (existingUser.isPresent()) {
                    // Existing user — just login normally
                    user = existingUser.get();
                    isNewUser = false;
                } else {
                    // New user — save with GUEST role
                    User newUser = User.builder()
                            .email(email)
                            .name(name)
                            .provider(Provider.GOOGLE)
                            .role(Role.GUEST)   // assign GUEST role temporarily
                            .build();
                    user = userRepository.save(newUser);
                    isNewUser = true;
                }
            }
            default-> throw new RuntimeException("Invalid Registration Id");
        }

        String jti= UUID.randomUUID().toString();
        RefreshToken refreshTokenOb = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .revoked(false)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .build();
        refreshTokenRepository.save(refreshTokenOb);
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user,refreshTokenOb.getJti());
        cookieService.attachRefreshCookie(response,refreshToken,(int)jwtService.getRefreshTtlSeconds());

        if (isNewUser) {
            // Send to complete profile page with userId so frontend knows who to update
            response.sendRedirect(frontendSuccessUrl + "/complete-profile?userId=" + user.getId());
        } else {
            response.sendRedirect(frontendSuccessUrl);
        }
    }
}
