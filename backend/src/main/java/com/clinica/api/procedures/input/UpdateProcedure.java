package com.clinica.api.procedures.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateProcedure(
        @NotNull(message = "Procedure id must be set")
        Integer procedureId,
        @NotEmpty(message = "Procedure desc cannot be blank")
        String procedureDesc
) {
}
