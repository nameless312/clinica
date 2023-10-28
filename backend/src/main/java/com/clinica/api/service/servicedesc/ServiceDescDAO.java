package com.clinica.api.service.servicedesc;

import java.util.List;
import java.util.Optional;

public interface ServiceDescDAO {
    Optional<ServiceDesc> selectServiceDescById(Integer id);
    List<ServiceDesc> selectServiceDescs();
    ServiceDesc insertServiceDesc(ServiceDesc serviceDesc);
    void updateServiceDesc(ServiceDesc serviceDesc);
}
