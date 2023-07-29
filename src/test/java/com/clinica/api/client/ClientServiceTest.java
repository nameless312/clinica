package com.clinica.api.client;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientDAO clientDAO;
    private ClientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClientService(clientDAO);
    }

    @Test
    void getAllClients() {
        // When
        underTest.getAllClients();

        // Then
        verify(clientDAO).selectAllClients();
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

//    @Test
//    void addCustomer() {
//        // Given
//        String email = "alex@gmail.com";
//
//        when(clientDAO.existsCustomerWithEmail(email)).thenReturn(false);
//
//        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
//                "Alex", email, 19
//        );
//
//        // When
//        underTest.addCustomer(request);
//
//        // Then
//        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
//                Customer.class
//        );
//
//        verify(clientDAO).insertCustomer(customerArgumentCaptor.capture());
//
//        Customer capturedCustomer = customerArgumentCaptor.getValue();
//
//        assertThat(capturedCustomer.getId()).isNull();
//        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
//        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
//    }
//
//    @Test
//    void willThrowWhenEmailExistsWhileAddingACustomer() {
//        // Given
//        String email = "alex@gmail.com";
//
//        when(clientDAO.existsCustomerWithEmail(email)).thenReturn(true);
//
//        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
//                "Alex", email, 19
//        );
//
//        // When
//        assertThatThrownBy(() -> underTest.addCustomer(request))
//                .isInstanceOf(DuplicateResourceException.class)
//                .hasMessage("email already taken");
//
//        // Then
//        verify(clientDAO, never()).insertCustomer(any());
//    }
//
//    @Test
//    void deleteCustomerById() {
//        // Given
//        int id = 10;
//
//        when(clientDAO.existsCustomerById(id)).thenReturn(true);
//
//        // When
//        underTest.deleteCustomerById(id);
//
//        // Then
//        verify(clientDAO).deleteCustomerById(id);
//    }
//
//    @Test
//    void willThrowDeleteCustomerByIdNotExists() {
//        // Given
//        int id = 10;
//
//        when(clientDAO.existsCustomerById(id)).thenReturn(false);
//
//        // When
//        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessage("customer with id [%s] not found".formatted(id));
//
//        // Then
//        verify(clientDAO, never()).deleteCustomerById(id);
//    }
//
//    @Test
//    void canUpdateAllCustomersProperties() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        String newEmail = "alexandro@amigoscode.com";
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                "Alexandro", newEmail, 23);
//
//        when(clientDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);
//
//        // When
//        underTest.updateCustomer(id, updateRequest);
//
//        // Then
//        ArgumentCaptor<Customer> customerArgumentCaptor =
//                ArgumentCaptor.forClass(Customer.class);
//
//        verify(clientDAO).updateCustomer(customerArgumentCaptor.capture());
//        Customer capturedCustomer = customerArgumentCaptor.getValue();
//
//        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
//        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
//    }
//
//    @Test
//    void canUpdateOnlyCustomerName() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                "Alexandro", null, null);
//
//        // When
//        underTest.updateCustomer(id, updateRequest);
//
//        // Then
//        ArgumentCaptor<Customer> customerArgumentCaptor =
//                ArgumentCaptor.forClass(Customer.class);
//
//        verify(clientDAO).updateCustomer(customerArgumentCaptor.capture());
//        Customer capturedCustomer = customerArgumentCaptor.getValue();
//
//        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
//        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
//    }
//
//    @Test
//    void canUpdateOnlyCustomerEmail() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        String newEmail = "alexandro@amigoscode.com";
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                null, newEmail, null);
//
//        when(clientDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);
//
//        // When
//        underTest.updateCustomer(id, updateRequest);
//
//        // Then
//        ArgumentCaptor<Customer> customerArgumentCaptor =
//                ArgumentCaptor.forClass(Customer.class);
//
//        verify(clientDAO).updateCustomer(customerArgumentCaptor.capture());
//        Customer capturedCustomer = customerArgumentCaptor.getValue();
//
//        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
//        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
//    }
//
//    @Test
//    void canUpdateOnlyCustomerAge() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                null, null, 22);
//
//        // When
//        underTest.updateCustomer(id, updateRequest);
//
//        // Then
//        ArgumentCaptor<Customer> customerArgumentCaptor =
//                ArgumentCaptor.forClass(Customer.class);
//
//        verify(clientDAO).updateCustomer(customerArgumentCaptor.capture());
//        Customer capturedCustomer = customerArgumentCaptor.getValue();
//
//        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
//        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
//    }
//
//    @Test
//    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        String newEmail = "alexandro@amigoscode.com";
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                null, newEmail, null);
//
//        when(clientDAO.existsCustomerWithEmail(newEmail)).thenReturn(true);
//
//        // When
//        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
//                .isInstanceOf(DuplicateResourceException.class)
//                .hasMessage("email already taken");
//
//        // Then
//        verify(clientDAO, never()).updateCustomer(any());
//    }
//
//    @Test
//    void willThrowWhenCustomerUpdateHasNoChanges() {
//        // Given
//        int id = 10;
//        Customer customer = new Customer(
//                id, "Alex", "alex@gmail.com", 19
//        );
//        when(clientDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
//
//        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
//                customer.getName(), customer.getEmail(), customer.getAge());
//
//        // When
//        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
//                .isInstanceOf(RequestValidationException.class)
//                .hasMessage("no data changes found");
//
//        // Then
//        verify(clientDAO, never()).updateCustomer(any());
//    }
}