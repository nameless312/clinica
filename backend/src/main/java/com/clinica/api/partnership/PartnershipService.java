package com.clinica.api.partnership;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
import com.clinica.api.partnership.input.NewPartnership;
import com.clinica.api.partnership.input.UpdatePartnership;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartnershipService {
    private final PartnershipDAO partnershipDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public PartnershipService(@Qualifier(value = "PartnershipJPA") PartnershipDAO partnershipDAO,
                              @Qualifier(value = "UserJPA") UserDAO userDAO,
                              Clock clock) {
        this.partnershipDAO = partnershipDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<PartnershipDTO> getAllPartnerships() {
        List<Partnership> partnerships = partnershipDAO.selectPartnerships();
        if (partnerships.isEmpty()) {
            return new ArrayList<>();
        }
        return partnerships.stream().map(Partnership::toDTO).collect(Collectors.toList());
    }

    public void insertPartnership(NewPartnership newPartnership) {
        Integer userId = newPartnership.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Partnership partnership = new Partnership();

        partnership.setUser(user);
        partnership.setPartner(newPartnership.partner());
        partnership.setMobile(newPartnership.mobile());
        partnership.setJob(newPartnership.job());
        partnership.setLocality(newPartnership.locality());
        partnership.setCommision(newPartnership.commission());

        partnershipDAO.insertPartnership(partnership);
    }

    public void updateParthenship(UpdatePartnership updatePartnership) {
        Integer partnershipId = updatePartnership.partnershipId();

        Partnership partnership = partnershipDAO.selectPartnershipById(partnershipId).
                orElseThrow(
                        () -> new ResourceNotFoundException("partnership with id [%s] not found".formatted(partnershipId))
                );

        partnership.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
        partnership.setPartner(updatePartnership.partner());
        partnership.setJob(updatePartnership.job());
        partnership.setLocality(updatePartnership.locality());
        partnership.setMobile(updatePartnership.mobile());
        partnership.setCommision(updatePartnership.commission());

        partnershipDAO.updatePartnership(partnership);
    }
}
