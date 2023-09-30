package com.clinica.api.service.servicetype;

public record ServiceTypeDTO(
        Integer serviceTypeID,
        String service_name,
        Double reference_cost
) {
}
