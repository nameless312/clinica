package com.clinica.api.services;

import com.clinica.api.dto.ClientDTO;
import com.clinica.api.entities.Client;
import com.clinica.api.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<ClientDTO> getClient(Integer id) {
        Optional<Client> client = clientRepository.findById(id);

        return client.map(Client::toDto);
    }

}
