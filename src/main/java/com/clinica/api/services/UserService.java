package com.clinica.api.services;

import com.clinica.api.dto.LoginRequest;
import com.clinica.api.dto.LoginResponse;
import com.clinica.api.dto.UserDTO;
import com.clinica.api.entities.User;
import com.clinica.api.repositories.UserRepository;
import com.clinica.api.services.auth.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new LoginResponse(jwtTokenService.generateToken(loginRequest.email()));
        } catch (AuthenticationException e) {
            return null;
        }
    }

    public UserDTO getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapUserToUserDTO).orElse(null);
    }

    private UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
