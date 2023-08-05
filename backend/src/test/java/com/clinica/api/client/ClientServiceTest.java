package com.clinica.api.client;

import com.clinica.api.address.Address;
import com.clinica.api.address.AddressDAO;
import com.clinica.api.address.AddressService;
import com.clinica.api.client.inputs.NewClient;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientDAO clientDAO;
    @Mock
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    @Mock
    private MarketingDAO marketingDAO;
    @Mock
    private PartnershipDAO partnershipDAO;
    @Mock
    private AddressService addressService;
    @Mock
    private Clock clock;
    private ClientService underTest;

    @BeforeEach
    void setUp() {
        clock = Clock.systemDefaultZone();
        underTest = new ClientService(clientDAO, addressService, addressDAO, marketingDAO, partnershipDAO, userDAO, clock);
    }

    @Test
    void canGetCustomer() {
        // Given
        int id = 10;
        Client client = Client.builder()
                .clientId(id)
                .dtAdded(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .dtRegistered(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .fullName("John Albert Smith")
                .nameAbbr("John Smith")
                .build();
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(client));

        // When
        ClientDTO actual = underTest.getClient(id);

        // Then
        assertThat(actual).isEqualTo(client.toDto());
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        int id = 10;

        when(clientDAO.selectClientById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getClient(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("client with id [%s] not found".formatted(id));
    }

    @Test
    void addClientWithAllOptions() {
        // Given
        Address address = new Address();
        address.setAddressId(1);

        User user = new User();
        user.setUserId(1);

        Partnership partnership = new Partnership();
        partnership.setPartnershipId(1);

        Marketing marketing = new Marketing();
        marketing.setMarketingId(1);

        String fullname = "John Doe Smith";
        String abbrName = "John Smith";
        String email = "a@a.com";
        Date birthdate = Date.from(Instant.now(clock));
        String mobile = "910012345";
        String lanline = "255098890";
        String gender = "Female";
        String ssn = "123456789";
        NewClient request = new NewClient(
                1,
                1,
                1,
                null,
                fullname,
                abbrName,
                email,
                birthdate,
                mobile,
                lanline,
                gender,
                ssn
        );

        Client savedClient = new Client();
        savedClient.setClientId(1);
        savedClient.setUser(user);
        savedClient.setPartnership(partnership);
        savedClient.setMarketing(marketing);
        savedClient.setFullName(fullname);
        savedClient.setNameAbbr(abbrName);
        savedClient.setEmail(email);
        savedClient.setBirthdate(birthdate);
        savedClient.setMobile(mobile);
        savedClient.setLanline(lanline);
        savedClient.setGender(Gender.getGender(gender));
        savedClient.setSsn(ssn);

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        when(partnershipDAO.selectPartnershipById(request.partnershipId())).thenReturn(Optional.of(partnership));
        when(marketingDAO.selectMarketingById(request.marketingId())).thenReturn(Optional.of(marketing));
        when(addressService.insertNewAddress(user, request.address())).thenReturn(address);
        when(clientDAO.insertClient(any(Client.class))).thenReturn(savedClient);
        underTest.insertClient(request);

        // Then
        ArgumentCaptor<Client> customerArgumentCaptor = ArgumentCaptor.forClass(
                Client.class
        );

        verify(clientDAO).insertClient(customerArgumentCaptor.capture());

        Client capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getClientId()).isNull();
        assertThat(capturedCustomer.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedCustomer.getMarketing().getMarketingId()).isEqualTo(request.marketingId());
        assertThat(capturedCustomer.getPartnership().getPartnershipId()).isEqualTo(request.partnershipId());
        assertThat(capturedCustomer.getAddress().getAddressId()).isNotNull();
        assertThat(capturedCustomer.getFullName()).isEqualTo(request.fullName());
        assertThat(capturedCustomer.getNameAbbr()).isEqualTo(request.nameAbbr());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getBirthdate()).isEqualTo(request.birthDate());
        assertThat(capturedCustomer.getMobile()).isEqualTo(request.mobile());
        assertThat(capturedCustomer.getLanline()).isEqualTo(request.lanline());
        assertThat(capturedCustomer.getGender()).isEqualTo(Gender.getGender(request.gender()));
        assertThat(capturedCustomer.getSsn()).isEqualTo(request.ssn());
    }
    @Test
    void addClientWithNoPartnership() {
        // Given
        Address address = new Address();
        address.setAddressId(1);

        User user = new User();
        user.setUserId(1);

        Marketing marketing = new Marketing();
        marketing.setMarketingId(1);

        String fullname = "John Doe Smith";
        String abbrName = "John Smith";
        String email = "a@a.com";
        Date birthdate = Date.from(Instant.now(clock));
        String mobile = "910012345";
        String lanline = "255098890";
        String gender = "Female";
        String ssn = "123456789";
        NewClient request = new NewClient(
                1,
                null,
                1,
                null,
                fullname,
                abbrName,
                email,
                birthdate,
                mobile,
                lanline,
                gender,
                ssn
        );

        Client savedClient = new Client();
        savedClient.setClientId(1);
        savedClient.setUser(user);
        savedClient.setMarketing(marketing);
        savedClient.setFullName(fullname);
        savedClient.setNameAbbr(abbrName);
        savedClient.setEmail(email);
        savedClient.setBirthdate(birthdate);
        savedClient.setMobile(mobile);
        savedClient.setLanline(lanline);
        savedClient.setGender(Gender.getGender(gender));
        savedClient.setSsn(ssn);

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        when(marketingDAO.selectMarketingById(request.marketingId())).thenReturn(Optional.of(marketing));
        when(addressService.insertNewAddress(user, request.address())).thenReturn(address);
        when(clientDAO.insertClient(any(Client.class))).thenReturn(savedClient);
        underTest.insertClient(request);

        // Then
        ArgumentCaptor<Client> customerArgumentCaptor = ArgumentCaptor.forClass(
                Client.class
        );

        verify(clientDAO).insertClient(customerArgumentCaptor.capture());

        Client capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getClientId()).isNull();
        assertThat(capturedCustomer.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedCustomer.getMarketing().getMarketingId()).isEqualTo(request.marketingId());
        assertThat(capturedCustomer.getPartnership()).isNull();
        assertThat(capturedCustomer.getAddress().getAddressId()).isNotNull();
        assertThat(capturedCustomer.getFullName()).isEqualTo(request.fullName());
        assertThat(capturedCustomer.getNameAbbr()).isEqualTo(request.nameAbbr());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getBirthdate()).isEqualTo(request.birthDate());
        assertThat(capturedCustomer.getMobile()).isEqualTo(request.mobile());
        assertThat(capturedCustomer.getLanline()).isEqualTo(request.lanline());
        assertThat(capturedCustomer.getGender()).isEqualTo(Gender.getGender(request.gender()));
        assertThat(capturedCustomer.getSsn()).isEqualTo(request.ssn());
    }
    @Test
    void addClientWithNoMarketing() {
        // Given
        Address address = new Address();
        address.setAddressId(1);

        User user = new User();
        user.setUserId(1);

        Partnership partnership = new Partnership();
        partnership.setPartnershipId(1);

        String fullname = "John Doe Smith";
        String abbrName = "John Smith";
        String email = "a@a.com";
        Date birthdate = Date.from(Instant.now(clock));
        String mobile = "910012345";
        String lanline = "255098890";
        String gender = "Female";
        String ssn = "123456789";
        NewClient request = new NewClient(
                1,
                1,
                null,
                null,
                fullname,
                abbrName,
                email,
                birthdate,
                mobile,
                lanline,
                gender,
                ssn
        );
        Client savedClient = new Client();
        savedClient.setClientId(1);
        savedClient.setUser(user);
        savedClient.setPartnership(partnership);
        savedClient.setFullName(fullname);
        savedClient.setNameAbbr(abbrName);
        savedClient.setEmail(email);
        savedClient.setBirthdate(birthdate);
        savedClient.setMobile(mobile);
        savedClient.setLanline(lanline);
        savedClient.setGender(Gender.getGender(gender));
        savedClient.setSsn(ssn);

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        when(partnershipDAO.selectPartnershipById(request.partnershipId())).thenReturn(Optional.of(partnership));
        when(addressService.insertNewAddress(user, request.address())).thenReturn(address);;
        when(clientDAO.insertClient(any(Client.class))).thenReturn(savedClient);
        underTest.insertClient(request);

        // Then
        ArgumentCaptor<Client> customerArgumentCaptor = ArgumentCaptor.forClass(
                Client.class
        );

        verify(clientDAO).insertClient(customerArgumentCaptor.capture());

        Client capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getClientId()).isNull();
        assertThat(capturedCustomer.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedCustomer.getMarketing()).isNull();
        assertThat(capturedCustomer.getPartnership().getPartnershipId()).isEqualTo(request.partnershipId());
        assertThat(capturedCustomer.getAddress().getAddressId()).isNotNull();
        assertThat(capturedCustomer.getFullName()).isEqualTo(request.fullName());
        assertThat(capturedCustomer.getNameAbbr()).isEqualTo(request.nameAbbr());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getBirthdate()).isEqualTo(request.birthDate());
        assertThat(capturedCustomer.getMobile()).isEqualTo(request.mobile());
        assertThat(capturedCustomer.getLanline()).isEqualTo(request.lanline());
        assertThat(capturedCustomer.getGender()).isEqualTo(Gender.getGender(request.gender()));
        assertThat(capturedCustomer.getSsn()).isEqualTo(request.ssn());
    }
    @Test
    void addClientWillThrowOnNonExistentPartnershipId() {
        // Given
        Integer partnershipId = -1;

        Address address = new Address();
        address.setAddressId(1);

        User user = new User();
        user.setUserId(1);

        Marketing marketing = new Marketing();
        marketing.setMarketingId(1);

        NewClient request = new NewClient(
                1,
                partnershipId,
                1,
                null,
                "John Doe Smith",
                "John Smith",
                "a@a.com",
                Date.from(Instant.now(clock)),
                "910012345",
                "255098890",
                "Female",
                "123456789"
        );

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        when(partnershipDAO.selectPartnershipById(request.partnershipId())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> {
            underTest.insertClient(request);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("partnership with id [%s] not found".formatted(partnershipId));
    }
    @Test
    void addClientWillThrowOnNonExistentMarketingId() {
        // Given
        Integer marketingId = -1;

        Address address = new Address();
        address.setAddressId(1);

        User user = new User();
        user.setUserId(1);

        Partnership partnership = new Partnership();
        partnership.setPartnershipId(1);


        NewClient request = new NewClient(
                1,
                1,
                marketingId,
                null,
                "John Doe Smith",
                "John Smith",
                "a@a.com",
                Date.from(Instant.now(clock)),
                "910012345",
                "255098890",
                "Female",
                "123456789"
        );

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        when(partnershipDAO.selectPartnershipById(request.partnershipId())).thenReturn(Optional.of(partnership));
        when(marketingDAO.selectMarketingById(request.marketingId())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> {
            underTest.insertClient(request);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("marketing with id [%s] not found".formatted(marketingId));
    }

    @Test
    void addClientWillThrowOnMissingUserId() {
        // Given
        Integer userId = -1;

        Address address = new Address();
        address.setAddressId(1);

        Partnership partnership = new Partnership();
        partnership.setPartnershipId(1);

        Marketing marketing = new Marketing();
        marketing.setMarketingId(1);

        NewClient request = new NewClient(
                userId,
                1,
                1,
                null,
                "John Doe Smith",
                "John Smith",
                "a@a.com",
                Date.from(Instant.now(clock)),
                "910012345",
                "255098890",
                "Female",
                "123456789"
        );

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> {
            underTest.insertClient(request);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("user with id [%s] not found".formatted(userId));
    }

    @Test
    void selectAllClientsEmpty() {
        // Given
        List<Client> clients = List.of();
        // When
        when(clientDAO.selectAllClients()).thenReturn(clients);

        // Then
        assertThat(underTest.getAllClients()).isEmpty();
    }
    @Test
    void selectAllClientsNotEmpty() {
        // Given
        Client client = new Client();
        client.setClientId(1);
        // When
        when(clientDAO.selectAllClients()).thenReturn(List.of(client));

        // Then
        List<ClientDTO> clients = underTest.getAllClients();
        assertThat(clients).isNotEmpty();
        assertThat(clients.get(0).clientID()).isEqualTo(client.getClientId());
    }
}