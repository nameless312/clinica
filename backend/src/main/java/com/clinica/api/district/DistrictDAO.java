package com.clinica.api.district;


import java.util.Optional;

public interface DistrictDAO {
    Optional<District> selectDistrictById(Integer id);
}
