package com.clinica.api.client;

import com.clinica.api.client.inputs.NewClient;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication, @PathVariable Integer id) {
        return ResponseEntity.ok().body(clientService.getClient(id));
    }
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(Authentication authentication) {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @PostMapping
    public ResponseEntity<Void> insertClient(Authentication authentication, @Valid @RequestBody NewClient newClient) {
        clientService.insertClient(newClient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
