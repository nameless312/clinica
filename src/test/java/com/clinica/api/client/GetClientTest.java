package com.clinica.api.client;

import com.clinica.api.dto.ClientDTO;
import com.clinica.api.dto.LoginRequest;
import com.clinica.api.helpers.AddressHelper;
import com.clinica.api.helpers.ClientHelper;
import com.clinica.api.helpers.UserHelper;
import com.clinica.api.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetClientTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private AddressHelper addressHelper;

    @Autowired
    private ClientHelper clientHelper;

    @PostConstruct
    @Transactional
    public void postConstruct() {
        userHelper.addNormalUser("john.doe@example.com", "password");
        addressHelper.addDistrict(1,1);
        addressHelper.addConcelho(1, 1,1);
        addressHelper.addAddress(1, 1,1,1);
        clientHelper.addClient(1, 1,1,null, null);
    }

    private ResultActions performGetClient(String id) throws Exception {
        // Perform the POST request to client endpoint with an id
        return mockMvc.perform(get("/api/v1/client/"+id));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "USER")
    public void testGetInvalidClient() {
        Optional<ClientDTO> client = clientService.getClient(-1);
        assertTrue(client.isEmpty());
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "USER")
    public void testGetValidClient() {
        Optional<ClientDTO> client = clientService.getClient(1);
        assertTrue(client.isPresent());
        assertEquals(1, client.get().clientID());
    }
    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "USER")
    public void testGetClientSuccessfulEndpoint() throws Exception {
        ResultActions resultActions = performGetClient("1");

        // Assert the successful get client response
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientID").value(1));
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "USER")
    public void testGetClientFailureEndpoint() throws Exception {
        ResultActions resultActions = performGetClient("-1");

        // Assert the successful get client response
        resultActions.andExpect(status().isNoContent());
    }
}
