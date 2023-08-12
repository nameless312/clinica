package com.clinica.api.auth;

import com.clinica.api.auth.jwt.TokenOutput;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        TokenOutput token = authService.authenticate(loginRequest);
        Cookie cookie = new Cookie("Authorization", token.token());
        cookie.setMaxAge((int) token.duration());
        cookie.setPath("/");
        cookie.setAttribute("SameSite","None");
        cookie.setSecure(true);
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.noContent().build();
    }
}
