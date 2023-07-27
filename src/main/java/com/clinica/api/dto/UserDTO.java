package com.clinica.api.dto;

public record UserDTO (
    Integer userId,
    String firstName,
    String lastName,
    String email
) {}
