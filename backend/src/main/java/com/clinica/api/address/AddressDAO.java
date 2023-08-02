package com.clinica.api.address;

import java.util.Optional;

public interface AddressDAO {
    Optional<Address> selectAddressById(Integer id);
    Address insertAddress(Address address);
}
