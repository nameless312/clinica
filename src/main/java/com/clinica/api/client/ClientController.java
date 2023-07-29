package com.clinica.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getClient(Authentication authentication, @PathVariable Integer id) {
        return ResponseEntity.ok().body(clientService.getClient(id));
    }
    @GetMapping("/")
    public ResponseEntity getClients(Authentication authentication) {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

}
