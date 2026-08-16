package com.ecom.authservice.service;

import com.ecom.authservice.dto.AuthRequest;
import com.ecom.authservice.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public String getToken() {
        return "token";
    }

    public String login(AuthRequest authRequest) {
        String username = authRequest.username();
        String password = authRequest.password();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        log.info("User: {} is authenticated", username);

        return jwtUtil.generateToken(username);

    }
}
