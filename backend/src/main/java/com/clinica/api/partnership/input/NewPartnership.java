package com.clinica.api.partnership.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPartnership(
        @NotNull(message = "User id must be set")
        Integer userId,
        @NotEmpty(message = "Partner cannot be blank")
        String partner,
        @NotEmpty(message = "Locality cannot be blank")
        String locality,
        @NotEmpty(message = "Mobile cannot be blank")
        String mobile,
        @NotNull(message = "Commission cannot be blank")
        Integer commission,
        @NotEmpty(message = "Job cannot be blank")
        String job
) {
}
