package com.clinica.api.service.servicedesc;

import com.clinica.api.procedure.ProcedureDTO;
import com.clinica.api.service.servicetype.ServiceTypeDTO;
import com.clinica.api.technique.TechniqueDTO;

import java.sql.Timestamp;

public record ServiceDescDTO(
        Integer serviceDescId,
        ServiceTypeDTO serviceType,
        ProcedureDTO procedure,
        TechniqueDTO technique,
        Timestamp dtService,
        Double serviceCost,
        Double securityDeposit,
        Double penalization,
        Double paidValue,
        Double commision,
        Integer discountPercentage,
        Double tip,
        Integer minutesUsed,
        Boolean usedCream,
        Integer numPills,
        String obs
) {
}