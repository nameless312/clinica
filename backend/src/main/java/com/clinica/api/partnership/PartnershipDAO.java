package com.clinica.api.partnership;

import com.clinica.api.marketing.Marketing;

import java.util.List;
import java.util.Optional;

public interface PartnershipDAO {
    Optional<Partnership> selectPartnershipById(Integer id);
    List<Partnership> selectPartnerships();
    Partnership insertPartnership(Partnership partnership);
    void updatePartnership(Partnership partnership);
}
