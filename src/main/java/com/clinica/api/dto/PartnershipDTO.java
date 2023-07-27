package com.clinica.api.dto;

public record PartnershipDTO(
        Integer partnershipID,
        String partner,
        String locality,
        String mobile,
        Integer commission,
        String job
) {
}
