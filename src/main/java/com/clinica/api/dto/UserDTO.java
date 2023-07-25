package com.clinica.api.dto;

import jakarta.persistence.Column;

public record UserDTO (
    Integer userId,
    String firstName,
    String lastName,
    String email
) {}
