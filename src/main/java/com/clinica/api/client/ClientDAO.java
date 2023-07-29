package com.clinica.api.client;

import java.util.Optional;

public interface ClientDAO {
    Optional<Client> selectClientById(Integer id);
}
