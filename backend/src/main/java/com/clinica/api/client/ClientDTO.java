package com.clinica.api.client;

import com.clinica.api.address.AddressDTO;
import com.clinica.api.marketing.MarketingDTO;
import com.clinica.api.partnership.PartnershipDTO;

import java.sql.Timestamp;
import java.util.Date;

public record ClientDTO(
        Integer clientID,
        AddressDTO address,
        MarketingDTO marketing,
        PartnershipDTO partnership,
        Timestamp dateRegistered,
        String fullName,
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
