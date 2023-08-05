package com.clinica.api.marketing;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MarketingJPA")
public class MarketingAccessJPADataAccessService implements MarketingDAO {

    private final MarketingRepository marketingRepository;

    public MarketingAccessJPADataAccessService(MarketingRepository marketingRepository) {
        this.marketingRepository = marketingRepository;

    }
    @Override
    public Optional<Marketing> selectMarketingChannelById(Integer id) {
        return marketingRepository.findById(id);
    }

    @Override
    public List<Marketing> selectMarketingChannels() {
        return marketingRepository.findAll();
    }

    @Override
    public void insertMarketingChannel(Marketing client) {
        marketingRepository.save(client);
    }

    @Override
    public void updateMarketingChannel(Marketing marketing) {
        marketingRepository.save(marketing);
    }
}
