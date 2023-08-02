package com.clinica.api.partnership;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("PartnershipJPA")
public class PartnershipAccessJPADataAccessService implements PartnershipDAO {

    private final PartnershipRepository partnershipRepository;

    public PartnershipAccessJPADataAccessService(PartnershipRepository partnershipRepository) {
        this.partnershipRepository = partnershipRepository;

    }
    @Override
    public Optional<Partnership> selectPartnershipById(Integer id) {
        return partnershipRepository.findById(id);
    }

    @Override
    public Partnership insertPartnership(Partnership partnership) {
        return partnershipRepository.save(partnership);
    }
}
