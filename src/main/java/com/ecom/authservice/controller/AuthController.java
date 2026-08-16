package com.ecom.authservice.controller;

import com.ecom.authservice.dto.AuthRequest;
import com.ecom.authservice.dto.AuthResponse;
import com.ecom.authservice.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest);

        AuthResponse authResponse = AuthResponse.builder().token(token).username(authRequest.username()).build();
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @GetMapping("/health")
    public String getHealth() {
        return "Healthy";
    }
}
