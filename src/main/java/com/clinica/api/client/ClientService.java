package com.clinica.api.client;

import com.clinica.api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {
    private final ClientDAO clientDAO;

    @Autowired
    public ClientService(@Qualifier(value = "JPA") ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientDTO getClient(Integer id) {
        Client client = clientDAO.selectClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "client with id [%s] not found".formatted(id)
                ));

        return client.toDto();
    }
    public List<ClientDTO> getAllClients() {
        List<Client> client = clientDAO.selectAllClients();

        return client.stream().map(Client::toDto).collect(Collectors.toList());
    }

}
