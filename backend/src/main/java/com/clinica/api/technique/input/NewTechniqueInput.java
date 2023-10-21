package com.clinica.api.technique.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewTechniqueInput(
        @NotNull(message = "User id must be set")
        Integer userId,
        @NotEmpty(message = "Technique name cannot be blank")
        String technique
) {
}
