package com.clinica.api.client.inputs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ClientUpdate(
        @NotNull(message = "Client id must be set")
        Integer clientId,
        Integer marketingId,
        Integer partnershipId,
        @NotNull(message = "Address id must be set")
        Integer addressId,
        @NotEmpty(message = "Full name must not be empty")
        String fullName,
        @NotEmpty(message = "Name abbreviation must not be empty")
        String nameAbbr,
        String email,
        Date birthDate,
        String mobile,
        String lanline,
        String notes,
        String gender,
        String ssn
) {
}
