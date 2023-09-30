package com.clinica.api.procedure.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewProcedure(
        @NotNull(message = "User id must be set")
        Integer userId,
        @NotEmpty(message = "ServiceType desc cannot be blank")
        String procedureDesc
) {
}
