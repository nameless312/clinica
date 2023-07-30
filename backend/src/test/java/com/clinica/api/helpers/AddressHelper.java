package com.clinica.api.helpers;

import com.clinica.api.address.Address;
import com.clinica.api.concelho.Concelho;
import com.clinica.api.district.District;
import com.clinica.api.address.AddressRepository;
import com.clinica.api.concelho.ConcelhoRepository;
import com.clinica.api.district.DistrictRepository;
import com.clinica.api.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class AddressHelper {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ConcelhoRepository concelhoRepository;

    @Transactional
    public void addAddress(Integer userId, Integer addressId, Integer districtId, Integer concelhoId) {
        // Save a dummy user to the database
        Address address = new Address();
        address.setUser(userRepository.findById(userId).get());
        address.setAddressId(addressId);
        address.setDistrict(districtRepository.findById(districtId).get());
        address.setConcelho(concelhoRepository.findById(concelhoId).get());
        address.setStreetName("Rua 123");
        address.setCity("Porto");
        address.setZipCode("4590-000");
        address.setLocality("Porto");
        address.setDtAdded(Timestamp.from(Instant.now()));
        addressRepository.save(address);
    }
    @Transactional
    public void addDistrict(Integer userId, Integer districtId) {
        // Save a dummy user to the database
        District district = new District();
        district.setUser(userRepository.findById(userId).get());
        district.setDistrictId(districtId);
        district.setDistrictName("Porto");
        district.setDtAdded(Timestamp.from(Instant.now()));
        districtRepository.save(district);
    }
    @Transactional
    public void addConcelho(Integer userId, Integer districtId, Integer concelhoId) {
        // Save a dummy user to the database
        Concelho concelho = new Concelho();
        concelho.setUser(userRepository.findById(userId).get());
        concelho.setConcelhoId(concelhoId);
        concelho.setDistrict(districtRepository.findById(districtId).get());
        concelho.setConcelhoName("Pacos de Ferreira");
        concelho.setDtAdded(Timestamp.from(Instant.now()));
        concelhoRepository.save(concelho);
    }

    @Transactional
    public void clearAddresses() {
        addressRepository.deleteAll();
        concelhoRepository.deleteAll();
        districtRepository.deleteAll();
    }
}
