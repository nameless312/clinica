package com.clinica.api.client;

import com.clinica.api.testcontainers.AbstractTestcontainers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientJPADataAccessServiceTest extends AbstractTestcontainers {
    private ClientAccessJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ClientAccessJPADataAccessService(clientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllClients() {
        // When
        underTest.selectAllClients();

        // Then
        verify(clientRepository).findAll();
    }

    @Test
    void selectClientById() {
        // Given
        int id = 1;

        // When
        underTest.selectClientById(id);

        // Then
        verify(clientRepository).findById(id);
    }

    @Test
    void insertClient() {
        // Given
        Client client = new Client();

        // When
        underTest.insertClient(client);

        // Then
        verify(clientRepository).save(client);
    }
}