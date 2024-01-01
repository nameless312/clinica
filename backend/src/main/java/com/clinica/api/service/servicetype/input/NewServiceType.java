package com.clinica.api.service.servicetype.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewServiceType(
        @NotNull(message = "User id must be set")
        Integer userId,
        @NotEmpty(message = "Service name cannot be blank")
        String serviceName,
        @Min(value = 0, message = "Reference cost cannot be less than 0")
        @NotNull(message = "Reference cost cannot be null")
        Double referenceCost
) {
}
