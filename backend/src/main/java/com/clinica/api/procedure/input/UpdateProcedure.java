package com.clinica.api.procedure.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateProcedure(
        @NotNull(message = "ServiceType id must be set")
        Integer procedureId,
        @NotEmpty(message = "ServiceType desc cannot be blank")
        String procedureDesc
) {
}
