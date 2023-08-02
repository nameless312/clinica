package com.clinica.api.address;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("AddressJPA")
public class AddressAccessJPADataAccessService implements AddressDAO {

    private final AddressRepository addressRepository;

    public AddressAccessJPADataAccessService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;

    }
    @Override
    public Optional<Address> selectAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address insertAddress(Address address) {
        return addressRepository.save(address);
    }
}
