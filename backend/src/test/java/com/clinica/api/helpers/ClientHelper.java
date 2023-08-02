package com.clinica.api.helpers;

import com.clinica.api.address.AddressRepository;
import com.clinica.api.client.Client;
import com.clinica.api.client.ClientRepository;
import com.clinica.api.marketing.MarketingRepository;
import com.clinica.api.partnership.PartnershipRepository;
import com.clinica.api.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class ClientHelper {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;
    @Autowired
    private MarketingRepository marketingRepository;

    @Transactional
    public void addClient(Integer clientId, Integer addressId, Integer userId, Integer partnershipId, Integer marketingId) {
        // Save a dummy user to the database
        Client client = new Client();
        client.setClientId(clientId);
        client.setUser(userRepository.findById(userId).get());
        client.setAddress(addressRepository.findById(addressId).get());
        if (partnershipId != null) {
            client.setPartnership(partnershipRepository.findById(partnershipId).get());
        }
        if (marketingId != null) {
            client.setMarketing(marketingRepository.findById(marketingId).get());
        }
        client.setFullName("John Mark Doe");
        client.setNameAbbr("John Doe");
        client.setEmail("a@a.com");
        client.setBirthdate(Timestamp.from(Instant.now()));
        client.setMobile("910234567");
        client.setLanline("255111222");
        client.setNotes("Very well behaved");
        client.setDtRegistered(Timestamp.from(Instant.now()));
        client.setSsn("299000111");
        client.setDtAdded(Timestamp.from(Instant.now()));
        clientRepository.save(client);
    }

    @Transactional
    public void clearclients() {
        clientRepository.deleteAll();
    }
}
