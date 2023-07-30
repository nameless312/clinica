package com.clinica.api.partnership;

public record PartnershipDTO(
        Integer partnershipID,
        String partner,
        String locality,
        String mobile,
        Integer commission,
        String job
) {
}
