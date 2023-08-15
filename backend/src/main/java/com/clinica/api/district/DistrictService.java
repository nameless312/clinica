package com.clinica.api.district;

import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DistrictService {
    private final DistrictDAO districtDAO;
    private final UserDAO userDAO;

    @Autowired
    public DistrictService(@Qualifier(value = "DistrictJPA") DistrictDAO districtDAO,
                           @Qualifier(value = "UserJPA") UserDAO userDAO,
                           Clock clock) {
        this.districtDAO = districtDAO;
        this.userDAO = userDAO;
    }

    public List<DistrictDTO> getAllDistricts() {
        List<District> districts = districtDAO.selectAllDistricts();
        if (districts.isEmpty()) {
            return new ArrayList<>();
        }
        return districts.stream().map(District::toDTO).collect(Collectors.toList());
    }
}
