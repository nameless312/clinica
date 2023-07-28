package com.clinica.api.auth;

import com.clinica.api.helpers.UserHelper;
import com.clinica.api.dto.LoginRequest;
import com.clinica.api.dto.LoginResponse;
import com.clinica.api.services.auth.AuthService;
import com.clinica.api.services.auth.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import jakarta.transaction.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
@ActiveProfiles("test")
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserHelper userHelper;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostConstruct
    public void postConstruct() {
        userHelper.addNormalUser("john.doe@example.com", "password");
    }
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void tearDown() {
        userHelper.clearUsers();
    }

    // Private login method for reuse
    private ResultActions performLogin(LoginRequest request) throws Exception {
        // Perform the POST request to /login endpoint
        return mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        // Prepare the login request payload for successful login
        LoginRequest request = new LoginRequest("john.doe@example.com", "password");

        // Call the private login method
        ResultActions resultActions = performLogin(request);

        // Assert the successful login response
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
    }

    @Test
    public void testFailedLogin() throws Exception {
        // Prepare the login request payload for failed login
        LoginRequest request = new LoginRequest("john.doe@example.com", "wrongPassword");

        // Call the private login method
        ResultActions resultActions = performLogin(request);

        // Assert the failed login response
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password."));
    }

    @Test
    public void testAuthenticationSuccess() {
        // Prepare the login request payload for successful login
        LoginRequest request = new LoginRequest("john.doe@example.com", "password");

        LoginResponse response = authService.authenticate(request);
        // Get the token from the response and verify its validity
        String responseToken = response.access_token();
        String email = jwtTokenService.validateTokenAndGetEmail(responseToken);

        // Assert the successful validation of the token
        assertEquals(request.email(), email);
    }
    @Test
    public void testAuthenticationFailure() {
        LoginRequest request = new LoginRequest("john.doe@example.com", "wrong_password");

        assertNull(authService.authenticate(request));
    }

    @Test
    public void testTokenExpiration() throws InterruptedException {

        jwtTokenService.setTokenValidity(Duration.ofSeconds(1));
        String token = jwtTokenService.generateToken("john.doe@example.com");

        // Verify that the token is valid immediately after generation
        String email = jwtTokenService.validateTokenAndGetEmail(token);
        assertEquals("john.doe@example.com", email);

        Thread.sleep(1000);
        // Verify that the token is now considered invalid (expired)
        String expiredEmail = jwtTokenService.validateTokenAndGetEmail(token);
        assertNull(expiredEmail);
    }
}
