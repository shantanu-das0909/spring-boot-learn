package com.ecom.authservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuthResponse(String username, String token) {
}
