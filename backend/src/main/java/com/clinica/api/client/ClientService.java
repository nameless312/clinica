package com.clinica.api.client;

import com.clinica.api.address.Address;
import com.clinica.api.address.AddressDAO;
import com.clinica.api.address.AddressRepository;
import com.clinica.api.address.AddressService;
import com.clinica.api.client.inputs.NewClient;
import com.clinica.api.client.inputs.NewClientAddress;
import com.clinica.api.concelho.Concelho;
import com.clinica.api.concelho.ConcelhoDAO;
import com.clinica.api.district.District;
import com.clinica.api.district.DistrictDAO;
import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.marketing.Marketing;
import com.clinica.api.marketing.MarketingDAO;
import com.clinica.api.partnership.Partnership;
import com.clinica.api.partnership.PartnershipDAO;
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
public class ClientService {
    private final ClientDAO clientDAO;
    private final AddressService addressService;
    private final MarketingDAO marketingDAO;
    private final PartnershipDAO partnershipDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public ClientService(@Qualifier(value = "ClientJPA") ClientDAO clientDAO,
                         AddressService addressService,
                         @Qualifier(value = "MarketingJPA") MarketingDAO marketingDAO,
                         @Qualifier(value = "PartnershipJPA") PartnershipDAO partnershipDAO,
                         @Qualifier(value = "UserJPA") UserDAO userDAO,
                         Clock clock) {
        this.clientDAO = clientDAO;
        this.addressService = addressService;
        this.marketingDAO = marketingDAO;
        this.partnershipDAO = partnershipDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public ClientDTO getClient(Integer id) {
        Client client = clientDAO.selectClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "client with id [%s] not found".formatted(id)
                ));

        return client.toDto();
    }
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientDAO.selectAllClients();
        if (clients.isEmpty()) {
            return new ArrayList<>();
        }
        return clients.stream().map(Client::toDto).collect(Collectors.toList());
    }

    public void insertClient(NewClient newClient) {
        Integer userId = newClient.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Partnership partnership = null;
        Integer partnershipId = newClient.partnershipId();
        if (partnershipId != null) {
            partnership = partnershipDAO.selectPartnershipById(partnershipId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("partnership with id [%s] not found".formatted(partnershipId))
                );
        }
        Marketing marketing = null;
        Integer marketingId = newClient.marketingId();
        if (marketingId != null) {
            marketing = marketingDAO.selectMarketingById(marketingId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("marketing with id [%s] not found".formatted(marketingId))
                );
        }
        Address address = addressService.insertNewAddress(user, newClient.address());

        Client client = new Client();

        client.setUser(user);
        client.setAddress(address);
        client.setPartnership(partnership);
        client.setMarketing(marketing);
        client.setFullName(newClient.fullName());
        client.setNameAbbr(newClient.nameAbbr());
        client.setEmail(newClient.email());
        client.setBirthdate(newClient.birthDate());
        client.setMobile(newClient.mobile());
        client.setLanline(newClient.lanline());
        client.setNotes(null);
        client.setGender(newClient.gender());
        client.setSsn(newClient.ssn());
        client.setDtRegistered(Timestamp.from(Instant.now(clock)));

        clientDAO.insertClient(client);
    }
}
