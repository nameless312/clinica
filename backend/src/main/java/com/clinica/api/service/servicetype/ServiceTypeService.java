package com.clinica.api.service.servicetype;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.service.servicetype.input.NewServiceType;
import com.clinica.api.service.servicetype.input.UpdateServiceType;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceTypeService {
    private final ServiceTypeDAO serviceTypeDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public ServiceTypeService(@Qualifier(value = "ServiceTypeJPA") ServiceTypeDAO serviceTypeDAO,
                              @Qualifier(value = "UserJPA") UserDAO userDAO,
                              Clock clock) {
        this.serviceTypeDAO = serviceTypeDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<ServiceTypeDTO> getAllServiceTypes() {
        List<ServiceType> serviceTypes = serviceTypeDAO.selectServiceTypes();
        if (serviceTypes.isEmpty()) {
            return new ArrayList<>();
        }
        return serviceTypes.stream().map(ServiceType::toDTO).collect(Collectors.toList());
    }

    public void insertServiceType(NewServiceType newServiceType) {
        Integer userId = newServiceType.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        ServiceType serviceType = new ServiceType();

        serviceType.setUser(user);
        serviceType.setServiceName(newServiceType.serviceName());
        serviceType.setReferenceCost(newServiceType.referenceCost());
        serviceTypeDAO.insertServiceType(serviceType);
    }

    public void updateServiceType(UpdateServiceType updateServiceType) {
        Integer serviceTypeId = updateServiceType.serviceTypeId();

        ServiceType serviceType = serviceTypeDAO.selectServiceTypeById(serviceTypeId).
                orElseThrow(
                        () -> new ResourceNotFoundException("serviceType with id [%s] not found".formatted(serviceTypeId))
                );

        serviceType.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
        serviceType.setServiceName(updateServiceType.serviceName());
        serviceType.setReferenceCost(updateServiceType.referenceCost());

        serviceTypeDAO.updateServiceType(serviceType);
    }
}
