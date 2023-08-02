package com.clinica.api.marketing;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("MarketingJPA")
public class MarketingAccessJPADataAccessService implements MarketingDAO {

    private final MarketingRepository marketingRepository;

    public MarketingAccessJPADataAccessService(MarketingRepository marketingRepository) {
        this.marketingRepository = marketingRepository;

    }
    @Override
    public Optional<Marketing> selectMarketingById(Integer id) {
        return marketingRepository.findById(id);
    }

    @Override
    public Marketing insertMarketing(Marketing client) {
        return marketingRepository.save(client);
    }
}
