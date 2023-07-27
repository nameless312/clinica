package com.clinica.api.dto;

public record AddressDTO(
        Integer addressID,
        Integer districtId,
        String districtName,
        Integer concelhoId,
        String concelhoName,
        String streetName,
        String city,
        String zipCode,
        String locality
) {
}
