package com.clinica.api.district;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("DistrictJPA")
public class DistrictAccessJPADataAccessService implements DistrictDAO {
    private final DistrictRepository districtRepository;

    public DistrictAccessJPADataAccessService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }
    @Override
    public Optional<District> selectDistrictById(Integer id) {
        return districtRepository.findById(id);
    }
}
