package com.clinica.api.service.servicedesc;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.service.servicedesc.input.NewServiceType;
import com.clinica.api.service.servicedesc.input.UpdateServiceType;
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
public class ServiceDescService {
    private final ServiceDescDAO serviceDescDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public ServiceDescService(@Qualifier(value = "ServiceDescJPA") ServiceDescDAO serviceDescDAO,
                              @Qualifier(value = "UserJPA") UserDAO userDAO,
                              Clock clock) {
        this.serviceDescDAO = serviceDescDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<ServiceDescDTO> getAllServiceDescs() {
        List<ServiceDesc> serviceDescs = serviceDescDAO.selectServiceDescs();
        if (serviceDescs.isEmpty()) {
            return new ArrayList<>();
        }
        return serviceDescs.stream().map(ServiceDesc::toDTO).collect(Collectors.toList());
    }

    public void insertServiceDesc(NewServiceType newServiceType) {
        Integer userId = newServiceType.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        ServiceDesc serviceDesc = new ServiceDesc();

//        serviceDesc.setUser(user);
//        serviceDesc.setServiceName(newServiceType.serviceName());
//        serviceDesc.setReferenceCost(newServiceType.referenceCost());
        serviceDescDAO.insertServiceDesc(serviceDesc);
    }

    public void updateServiceDesc(UpdateServiceType updateServiceType) {
        Integer serviceDescId = updateServiceType.serviceTypeId();

        ServiceDesc serviceDesc = serviceDescDAO.selectServiceDescById(serviceDescId).
                orElseThrow(
                        () -> new ResourceNotFoundException("serviceDesc with id [%s] not found".formatted(serviceDescId))
                );

        serviceDesc.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
//        serviceDesc.setServiceName(updateServiceType.serviceName());
//        serviceDesc.setReferenceCost(updateServiceType.referenceCost());

        serviceDescDAO.updateServiceDesc(serviceDesc);
    }
}
