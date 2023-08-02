package com.clinica.api.address;

import com.clinica.api.client.inputs.NewClientAddress;
import com.clinica.api.concelho.Concelho;
import com.clinica.api.concelho.ConcelhoDAO;
import com.clinica.api.district.District;
import com.clinica.api.district.DistrictDAO;
import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@Transactional
public class AddressService {
    private final AddressDAO addressDao;
    private final DistrictDAO districtDAO;
    private final ConcelhoDAO concelhoDAO;
    private final Clock clock;

    @Autowired
    public AddressService(@Qualifier(value = "AddressJPA") AddressDAO addressDao,
                          @Qualifier(value = "DistrictJPA") DistrictDAO districtDAO,
                          @Qualifier(value = "ConcelhoJPA") ConcelhoDAO concelhoDAO,
                          Clock clock) {
        this.addressDao = addressDao;
        this.districtDAO = districtDAO;
        this.concelhoDAO = concelhoDAO;
        this.clock = clock;
    }
    public Address insertNewAddress(User user, NewClientAddress newAddress) {
        Integer districtId = newAddress.districtId();
        Integer concelhoId = newAddress.concelhoId();
        District district = districtDAO.selectDistrictById(districtId).
                orElseThrow(
                        () -> new ResourceNotFoundException("district with id [%s] not found".formatted(districtId))
                );
        Concelho concelho = concelhoDAO.selectConcelhoById(newAddress.concelhoId()).
                orElseThrow(
                        () -> new ResourceNotFoundException("concelho with id [%s] not found".formatted(concelhoId))
                );

        Address address = new Address();

        address.setDistrict(district);
        address.setConcelho(concelho);
        address.setUser(user);
        address.setStreetName(newAddress.streetName());
        address.setCity(newAddress.city());
        address.setZipCode(newAddress.zipCode());
        address.setLocality(newAddress.locality());

        return addressDao.insertAddress(address);
    }
}
