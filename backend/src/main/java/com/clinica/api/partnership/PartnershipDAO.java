package com.clinica.api.partnership;

import java.util.Optional;

public interface PartnershipDAO {
    Optional<Partnership> selectPartnershipById(Integer id);
    Partnership insertPartnership(Partnership partnership);
}
