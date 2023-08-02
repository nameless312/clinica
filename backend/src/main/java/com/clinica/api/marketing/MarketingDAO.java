package com.clinica.api.marketing;

import java.util.Optional;

public interface MarketingDAO {
    Optional<Marketing> selectMarketingById(Integer id);
    Marketing insertMarketing(Marketing marketing);
}
