package com.clinica.api.auth;

import com.clinica.api.auth.jwt.JwtTokenService;
import com.clinica.api.exceptions.InvalidCredentialsException;
import com.clinica.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenService.generateToken(loginRequest.email(), ((User) authentication.getPrincipal()).getUserId());
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
    }
}
