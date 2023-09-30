package com.clinica.api.service.servicetype;

import java.util.List;
import java.util.Optional;

public interface ServiceTypeDAO {
    Optional<ServiceType> selectServiceTypeById(Integer id);
    List<ServiceType> selectServiceTypes();
    ServiceType insertServiceType(ServiceType serviceType);
    void updateServiceType(ServiceType serviceType);
}
