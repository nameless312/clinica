package com.clinica.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private final ClientDAO clientDAO;

    @Autowired
    public ClientService(@Qualifier(value = "JPA") ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Optional<ClientDTO> getClient(Integer id) {
        Optional<Client> client = clientDAO.selectClientById(id);

        return client.map(Client::toDto);
    }

}
