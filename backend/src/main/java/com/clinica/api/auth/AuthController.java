package com.clinica.api.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok().headers(httpHeaders -> httpHeaders.setBearerAuth(token)).build();

    }
}
