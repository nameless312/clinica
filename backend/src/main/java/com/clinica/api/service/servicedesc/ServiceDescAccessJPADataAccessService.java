package com.clinica.api.service.servicedesc;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ServiceDescJPA")
public class ServiceDescAccessJPADataAccessService implements ServiceDescDAO {

    private final ServiceDescRepository serviceDescRepository;

    public ServiceDescAccessJPADataAccessService(ServiceDescRepository serviceDescRepository) {
        this.serviceDescRepository = serviceDescRepository;

    }
    @Override
    public Optional<ServiceDesc> selectServiceDescById(Integer id) {
        return serviceDescRepository.findById(id);
    }

    @Override
    public List<ServiceDesc> selectServiceDescs() {
        return serviceDescRepository.findAll();
    }

    @Override
    public ServiceDesc insertServiceDesc(ServiceDesc serviceDesc) {
        return serviceDescRepository.save(serviceDesc);
    }

    @Override
    public void updateServiceDesc(ServiceDesc serviceDesc) {
        serviceDescRepository.save(serviceDesc);
    }
}
