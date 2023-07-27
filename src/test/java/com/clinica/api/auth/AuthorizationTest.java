package com.clinica.api.auth;

import com.clinica.api.entities.User;
import com.clinica.api.repositories.UserRepository;
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

import java.sql.Timestamp;
import java.time.Instant;

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
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        if (userRepository.count() == 0) {
            // Save a dummy user to the database
            User dummyUser = new User();
            dummyUser.setFirstName("John");
            dummyUser.setLastName("Doe");
            dummyUser.setEmail("john.doe@example.com");
            // password
            dummyUser.setPassword("$2a$10$GrfBKUxRr9XD71yYOaMI7Ooxd1nFHQ8/VtcVeOmYINyHzVIp7tLjC");
            dummyUser.setDtAdded(Timestamp.from(Instant.now()));
            dummyUser.setRole("ROLE_USER");
            dummyUser.setEnabled((short) 1);
            userRepository.save(dummyUser);
        }
    }

    // Private login method for reuse
    private ResultActions getUser(String id) throws Exception {
        // Perform the GET request to /user/id endpoint
        return mockMvc.perform(get("/api/v1/user/"+ id));
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "USER")
    public void testSuccessfulAuthorization() throws Exception {
        // Try to make a get request for a secure endpoint
        ResultActions resultActions = getUser("1");

        // Assert the successful Authorization response
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", roles = "")
    public void testFailedAuthorization() throws Exception {
        // Try to make a get request for a secure endpoint
        ResultActions resultActions = getUser("1");

        // Assert the failed Authorization response
        resultActions.andExpect(status().isForbidden());
    }
}
