package com.clinica.api.service.servicetype;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.service.servicetype.ServiceType;
import com.clinica.api.service.servicetype.ServiceTypeDAO;
import com.clinica.api.service.servicetype.ServiceTypeDTO;
import com.clinica.api.service.servicetype.ServiceTypeService;
import com.clinica.api.service.servicetype.input.NewServiceType;
import com.clinica.api.service.servicetype.input.UpdateServiceType;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTypeServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private ServiceTypeDAO serviceTypeDAO;
    @Mock
    private Clock clock;
    private ServiceTypeService underTest;

    @BeforeEach
    void setUp() {
        clock = Clock.systemDefaultZone();
        underTest = new ServiceTypeService(serviceTypeDAO, userDAO, clock);
    }
    @Test
    void selectAllServiceTypesEmpty() {
        // Given
        List<ServiceType> serviceTypes = List.of();

        // When
        when(serviceTypeDAO.selectServiceTypes()).thenReturn(serviceTypes);

        // Then
        assertThat(underTest.getAllServiceTypes()).isEmpty();
    }
    @Test
    void selectAllServiceTypesEmptyNotEmpty() {
        // Given
        int id = 10;
        ServiceType serviceType = ServiceType.builder()
                .serviceTypeId(id)
                .dtAdded(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .dtUpdate(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .serviceName("Corte de Cabelo")
                .referenceCost(1D)
                .build();

        // When
        when(serviceTypeDAO.selectServiceTypes()).thenReturn(List.of(serviceType));

        // Then
        List<ServiceTypeDTO> channels = underTest.getAllServiceTypes();
        assertThat(channels).isNotEmpty();
        assertThat(channels.get(0).serviceTypeID()).isEqualTo(serviceType.getServiceTypeId());
    }

    @Test
    void willThrowWhenInsertServiceTypeHasNonExistingUserId() {
        // Given
        int id = 10;
        NewServiceType newServiceType =
                new NewServiceType(id, "This and That", 1D);

        // When
        // Then
        assertThatThrownBy(() -> underTest.insertServiceType(newServiceType))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user with id [%s] not found".formatted(id));
    }

    @Test
    void insertServiceTypeWithAllOptions() {
        // Given
        User user = new User();
        user.setUserId(1);

        NewServiceType request =
                new NewServiceType(1, "This and That", 1D);

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        underTest.insertServiceType(request);

        // Then
        ArgumentCaptor<ServiceType> serviceTypeArgumentCaptor = ArgumentCaptor.forClass(
                ServiceType.class
        );

        verify(serviceTypeDAO).insertServiceType(serviceTypeArgumentCaptor.capture());

        ServiceType capturedServiceType = serviceTypeArgumentCaptor.getValue();

        assertThat(capturedServiceType.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedServiceType.getServiceName()).isEqualTo(request.serviceName());
        assertThat(capturedServiceType.getReferenceCost()).isEqualTo(request.referenceCost());
    }

    @Test
    void updateServiceTypeWillThrowOnNonExistingServiceTypeId(){
        // Given
        int id = 10;
        UpdateServiceType updateServiceType = new UpdateServiceType(id, "This and that", 1D);

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateServiceType(updateServiceType))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("serviceType with id [%s] not found".formatted(id));
    }

    @Test
    void updateServiceTypeWillSucceed() {
        // Given
        int id = 1;
        String serviceName = "This and That";
        double referenceCost = 1D;
        UpdateServiceType request = new UpdateServiceType(id, serviceName, referenceCost);

        ServiceType ServiceType = new ServiceType();
        ServiceType.setServiceTypeId(1);
        ServiceType.setServiceName("test");
        ServiceType.setReferenceCost(2D);

        // When
        when(serviceTypeDAO.selectServiceTypeById(id)).thenReturn(Optional.of(ServiceType));

        // Call the method to be tested
        underTest.updateServiceType(request);

        // Then
        ArgumentCaptor<ServiceType> ServiceTypeArgumentCaptor = ArgumentCaptor.forClass(ServiceType.class);
        verify(serviceTypeDAO).updateServiceType(ServiceTypeArgumentCaptor.capture());

        ServiceType capturedServiceType = ServiceTypeArgumentCaptor.getValue();

        assertThat(capturedServiceType.getServiceName()).isEqualTo(serviceName);
        assertThat(capturedServiceType.getReferenceCost()).isEqualTo(referenceCost);
        assertThat(capturedServiceType.getDtUpdate()).isAfterOrEqualTo(ServiceType.getDtUpdate());
    }
}