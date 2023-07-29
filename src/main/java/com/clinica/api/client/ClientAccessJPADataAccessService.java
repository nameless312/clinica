package com.clinica.api.client;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("JPA")
public class ClientAccessJPADataAccessService implements ClientDAO {

    private final ClientRepository clientRepository;

    public ClientAccessJPADataAccessService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public Optional<Client> selectClientById(Integer id) {
        return clientRepository.findById(id);
    }
}
