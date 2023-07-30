package com.clinica.api.user;

public record UserDTO (
    Integer userId,
    String firstName,
    String lastName,
    String email
) {}
