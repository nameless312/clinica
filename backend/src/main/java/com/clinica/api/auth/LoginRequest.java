package com.clinica.api.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email must be set")
        String email,
        @NotBlank(message = "password must be set")
        String password
){}
