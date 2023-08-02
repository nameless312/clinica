package com.clinica.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Transactional
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(Authentication authentication, @PathVariable Integer id) {
        UserDTO user = userService.getUser(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
