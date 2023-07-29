package com.clinica.api.auth;

import com.clinica.api.helpers.UserHelper;
import jakarta.annotation.PostConstruct;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import jakarta.transaction.Transactional;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthorizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserHelper userHelper;

    @PostConstruct
    public void postConstruct() {
        userHelper.addNormalUser("john.doe@example.com", "password");
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Private login method for reuse
    private ResultActions getUser(String id) throws Exception {
        // Perform the GET request to /user/id endpoint
        return mockMvc.perform(get("/api/v1/user/"+ id));
    }

//    @Test
//    @WithMockUser(username = "john.doe@example.com", roles = "USER")
//    public void testSuccessfulAuthorization() throws Exception {
//        // Try to make a get request for a secure endpoint
//        ResultActions resultActions = getUser("1");
//
//        // Assert the successful Authorization response
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value("1"))
//                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
//    }
//
//    @Test
//    @WithMockUser(username = "john.doe@example.com", roles = "")
//    public void testFailedAuthorization() throws Exception {
//        // Try to make a get request for a secure endpoint
//        ResultActions resultActions = getUser("1");
//
//        // Assert the failed Authorization response
//        resultActions.andExpect(status().isForbidden());
//    }
}
