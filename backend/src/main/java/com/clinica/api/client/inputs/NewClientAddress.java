package com.clinica.api.client.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewClientAddress(
        @NotBlank(message = "Street name must be set")
        String streetName,
        @NotBlank(message = "City must be set")
        String city,
        @NotBlank(message = "Zip code must be set")
        String zipCode,
        @NotBlank(message = "Locality must be set")
        String locality,
        @NotNull(message = "District id abbreviation must be set")
        Integer districtId,
        @NotNull(message = "Concelho id abbreviation must be set")
        Integer concelhoId

) {
}
