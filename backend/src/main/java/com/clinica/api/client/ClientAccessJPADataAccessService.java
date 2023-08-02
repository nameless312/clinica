package com.clinica.api.client;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ClientJPA")
public class ClientAccessJPADataAccessService implements ClientDAO {

    private final ClientRepository clientRepository;

    public ClientAccessJPADataAccessService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }
    @Override
    public Optional<Client> selectClientById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> selectAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void insertClient(Client client) {
        clientRepository.save(client);
    }
}
