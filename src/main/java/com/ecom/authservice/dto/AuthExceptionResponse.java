package com.ecom.authservice.dto;

import lombok.Builder;

@Builder
public record AuthExceptionResponse(String error, String message, String status) {}
