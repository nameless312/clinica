package com.clinica.api.technique.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateTechnique(
        @NotNull(message = "Technique id must be set")
        Integer techniqueId,
        @NotEmpty(message = "Technique name cannot be blank")
        String technique
) {
}
