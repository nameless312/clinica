package com.clinica.api.service.servicetype;

import com.clinica.api.procedure.ProcedureRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ServiceTypeJPA")
public class ServiceTypeAccessJPADataAccessService implements ServiceTypeDAO {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeAccessJPADataAccessService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;

    }
    @Override
    public Optional<ServiceType> selectServiceTypeById(Integer id) {
        return serviceTypeRepository.findById(id);
    }

    @Override
    public List<ServiceType> selectServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public ServiceType insertServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    @Override
    public void updateServiceType(ServiceType serviceType) {
        serviceTypeRepository.save(serviceType);
    }
}
