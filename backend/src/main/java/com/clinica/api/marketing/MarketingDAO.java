package com.clinica.api.marketing;

import java.util.List;
import java.util.Optional;

public interface MarketingDAO {
    Optional<Marketing> selectMarketingChannelById(Integer id);
    List<Marketing> selectMarketingChannels();
    void insertMarketingChannel(Marketing marketing);
    void updateMarketingChannel(Marketing marketing);
}
