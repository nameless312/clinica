package com.clinica.api.district;


import java.util.List;
import java.util.Optional;

public interface DistrictDAO {
    Optional<District> selectDistrictById(Integer id);
    List<District> selectAllDistricts();
}
