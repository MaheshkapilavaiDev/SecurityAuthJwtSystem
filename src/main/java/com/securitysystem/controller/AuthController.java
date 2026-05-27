package com.securitysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securitysystem.dto.LoginRequest;
import com.securitysystem.dto.RefreshTokenRequest;
import com.securitysystem.dto.RegisterRequest;
import com.securitysystem.dto.TokenResponse;
import com.securitysystem.entity.User;
import com.securitysystem.repository.UserRepository;
import com.securitysystem.service.AuthService;
import com.securitysystem.service.JwtService;
import com.securitysystem.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	
	@Autowired
    private  AuthService authService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        if (!refreshTokenService.validateToken(
                request.getRefreshToken())) {

            throw new RuntimeException(
                    "Invalid Refresh Token"
            );
        }

        String username =
                jwtService.extractUsername(
                        request.getRefreshToken()
                );

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow();

        String accessToken =
                jwtService.generateToken(user);

        return ResponseEntity.ok(
                new TokenResponse(
                        accessToken,
                        request.getRefreshToken()
                )
        );
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody RefreshTokenRequest request) {

        refreshTokenService.revokeToken(
                request.getRefreshToken()
        );

        return ResponseEntity.ok(
                "Logged Out Successfully"
        );
    }
}
