package com.clinica.api.client.inputs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record NewClient(
        @NotNull(message = "User id must be set")
        Integer userId,
        Integer partnershipId,
        Integer marketingId,
        @Valid
        @NotNull(message = "Address must be set")
        NewClientAddress address,
        @NotBlank(message = "Full name must be set")
        String fullName,
        @NotBlank(message = "Name abbreviation must be set")
        String nameAbbr,
        String email,
        Date birthDate,
        String mobile,
        String lanline,
        String gender,
        String ssn
) {
}
