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
//
//    @Test
//    void insertCustomer() {
//        //  Given
//        Client customer = new Client(
//                1, "Ali", "ali@gmail.com", 2
//        );
//
//        // When
//        underTest.insertCustomer(customer);
//
//        // Then
//        verify(customerRepository).save(customer);
//    }
//
//    @Test
//    void existsCustomerWithEmail() {
//        // Given
//        String email = "foo@gmail.com";
//
//        // When
//        underTest.existsCustomerWithEmail(email);
//
//        // Then
//        verify(customerRepository).existsCustomerByEmail(email);
//    }
//
//    @Test
//    void existsCustomerById() {
//        // Given
//        int id = 1;
//
//        // When
//        underTest.existsCustomerById(id);
//
//        // Then
//        verify(customerRepository).existsCustomerById(id);
//    }
//
//    @Test
//    void deleteCustomerById() {
//        // Given
//        int id = 1;
//
//        // When
//        underTest.deleteCustomerById(id);
//
//        // Then
//        verify(customerRepository).deleteById(id);
//    }
//
//    @Test
//    void updateCustomer() {
//        // Given
//        Customer customer = new Customer(
//                1, "Ali", "ali@gmail.com", 2
//        );
//
//        // When
//        underTest.updateCustomer(customer);
//
//        // Then
//        verify(customerRepository).save(customer);
//    }
}