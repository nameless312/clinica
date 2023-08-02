package com.clinica.api.address;

import com.clinica.api.client.inputs.NewClientAddress;
import com.clinica.api.user.User;

import java.util.Optional;

public interface AddressDAO {
    Optional<Address> selectAddressById(Integer id);
    Address insertAddress(Address address);
}
