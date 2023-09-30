package com.clinica.api.address.concelho;

import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Clock;
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
