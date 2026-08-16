package com.ecom.authservice.util;

import com.ecom.authservice.exception.JWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    @Value("${jwt.expiration-time}")
    private Duration expirationTime;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    public String generateToken(String username) {
        log.debug("Creating JWT token for User ID: {}", username);

        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + expirationTime.toMillis();
        try {
            String token = Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date(currentTimeMillis))
                    .expiration(new Date(expirationTimeMillis))
                    .signWith(secretKey, Jwts.SIG.HS256)
                    .compact();

            log.debug("JWT token successfully created for User ID: {} | token: {}", username, token);
            return token;
        } catch (Exception e) {
            log.error("Token creation failed", e);
            throw new JWTException("JWT token creation failed");
        }
    }

    public Claims extractAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractAllClaims(token).getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
















