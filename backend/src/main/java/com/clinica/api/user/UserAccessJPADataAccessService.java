package com.clinica.api.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserJPA")
public class UserAccessJPADataAccessService implements UserDAO {
    private final UserRepository userRepository;

    public UserAccessJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<User> selectUserById(Integer id) {
        return userRepository.findById(id);
    }
}
