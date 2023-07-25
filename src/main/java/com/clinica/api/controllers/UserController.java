package com.clinica.api.controllers;

import com.clinica.api.dto.UserDTO;
import com.clinica.api.entities.User;
import com.clinica.api.dto.LoginRequest;
import com.clinica.api.dto.LoginResponse;
import com.clinica.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.authenticate(loginRequest);
        if (loginResponse != null) {
            return ResponseEntity.ok().body(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(Authentication authentication, @PathVariable Integer id) {
        UserDTO user = userService.getUser(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
