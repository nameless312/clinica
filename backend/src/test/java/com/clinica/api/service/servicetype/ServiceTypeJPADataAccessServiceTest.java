package com.clinica.api.service.servicetype;

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
public class ServiceTypeJPADataAccessServiceTest extends AbstractTestcontainers {
    private ServiceTypeAccessJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ServiceTypeAccessJPADataAccessService(serviceTypeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllServiceTypes() {
        // When
        underTest.selectServiceTypes();

        // Then
        verify(serviceTypeRepository).findAll();
    }

    @Test
    void selectServiceTypeById() {
        // Given
        int id = 1;

        // When
        underTest.selectServiceTypeById(id);

        // Then
        verify(serviceTypeRepository).findById(id);
    }

    @Test
    void insertServiceType() {
        // Given
        ServiceType serviceType = new ServiceType();

        // When
        underTest.insertServiceType(serviceType);

        // Then
        verify(serviceTypeRepository).save(serviceType);
    }
    @Test
    void updateServiceType() {
        // Given
        ServiceType serviceType = new ServiceType();

        // When
        underTest.updateServiceType(serviceType);

        // Then
        verify(serviceTypeRepository).save(serviceType);
    }
}