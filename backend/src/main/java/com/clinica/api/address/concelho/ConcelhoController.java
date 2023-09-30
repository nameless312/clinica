package com.clinica.api.address.concelho;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/concelho")
public class ConcelhoController {
    private final ConcelhoService concelhoService;

    @Autowired
    public ConcelhoController(ConcelhoService concelhoService) {
        this.concelhoService = concelhoService;
    }

    @GetMapping
    public ResponseEntity<List<ConcelhoDTO>> getAllConcelhos(Authentication authentication) {
        return ResponseEntity.ok().body(concelhoService.getAllConcelhos());
    }
}
