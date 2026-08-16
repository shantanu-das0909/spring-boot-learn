package com.ecom.authservice.exception;

import com.ecom.authservice.dto.AuthExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Consolidated Core Authentication Failures (401 Unauthorized)
     * Handles credentials mismatch, non-existent users, and general security layer rejections.
     */
    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            AuthenticationException.class
    })
    public ResponseEntity<@NonNull AuthExceptionResponse> handleAuthenticationFailures(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed", ex.getMessage());
    }

    /**
     * JWT Structural & Signature Failures (401 Unauthorized)
     * Handles tempered keys, invalid signatures, or corrupt/malformed strings.
     */
    @ExceptionHandler({
            SignatureException.class,
            MalformedJwtException.class
    })
    public ResponseEntity<@NonNull AuthExceptionResponse> handleInvalidTokens(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed", "Token signature or structure is invalid");
    }

    /**
     * JWT Expiration (401 Unauthorized)
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<@NonNull AuthExceptionResponse> handleExpiredToken(ExpiredJwtException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed", "Token has expired");
    }

    /**
     * Disabled Account Restrictions (403 Forbidden)
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<@NonNull AuthExceptionResponse> handleDisabledException(DisabledException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Authentication failed", ex.getMessage());
    }

    /**
     * The Leftover Catch-All Fallback (500 Internal Server Error)
     * Intercepts unmapped runtime, database, or environmental problems safely.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull AuthExceptionResponse> handleGenericFallback(Exception ex) {
        log.error("Unhandled exception caught by global fallback: ", ex);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected processing error occurred on the server."
        );
    }

    /**
     * Centralized builder utility to eliminate boilerplate response formatting.
     */
    private ResponseEntity<@NonNull AuthExceptionResponse> buildResponse(HttpStatus status, String errorLabel, String message) {
        AuthExceptionResponse responseBody = AuthExceptionResponse.builder()
                .error(errorLabel)
                .message(message)
                .status(String.valueOf(status.value())) // Output: "401", "403", "500"
                .build();

        return ResponseEntity.status(status).body(responseBody);
    }
}
