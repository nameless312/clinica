package com.clinica.api.concelho;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.marketing.Marketing;
import com.clinica.api.marketing.MarketingDAO;
import com.clinica.api.marketing.MarketingDTO;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
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
public class ConcelhoService {
    private final ConcelhoDAO concelhoDAO;
    private final UserDAO userDAO;

    @Autowired
    public ConcelhoService(@Qualifier(value = "ConcelhoJPA") ConcelhoDAO concelhoDAO,
                           @Qualifier(value = "UserJPA") UserDAO userDAO,
                           Clock clock) {
        this.concelhoDAO = concelhoDAO;
        this.userDAO = userDAO;
    }

    public List<ConcelhoDTO> getAllConcelhos() {
        List<Concelho> concelhos = concelhoDAO.selectAllConcelhos();
        if (concelhos.isEmpty()) {
            return new ArrayList<>();
        }
        return concelhos.stream().map(Concelho::toDTO).collect(Collectors.toList());
    }
}
