package com.clinica.api.user;

import com.clinica.api.user.UserDTO;
import com.clinica.api.user.User;
import com.clinica.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserDTO getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapUserToUserDTO).orElse(null);
    }

    private UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
