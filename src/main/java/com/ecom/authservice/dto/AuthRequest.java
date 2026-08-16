package com.ecom.authservice.dto;

import lombok.Builder;

@Builder
public record AuthRequest(String username, String password) {
}
