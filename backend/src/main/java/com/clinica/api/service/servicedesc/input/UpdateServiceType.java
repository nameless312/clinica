package com.clinica.api.service.servicedesc.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateServiceType(
        @NotNull(message = "Technique id must be set")
        Integer serviceTypeId,
        @NotEmpty(message = "Service name cannot be blank")
        String serviceName,

        @Min(value = 0, message = "Reference cost cannot be less than 0")
        @NotNull(message = "Reference cost cannot be null")
        Double referenceCost
) {
}
