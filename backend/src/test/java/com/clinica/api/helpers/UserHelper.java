package com.clinica.api.helpers;

import com.clinica.api.user.User;
import com.clinica.api.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
public class UserHelper {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addNormalUser(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            // Save a dummy user to the database
            User dummyUser = new User();
            dummyUser.setUserId(1);
            dummyUser.setFirstName("John");
            dummyUser.setLastName("Doe");
            dummyUser.setEmail(email);
            // password
            dummyUser.setPassword(new BCryptPasswordEncoder().encode(password));
            dummyUser.setDtAdded(Timestamp.from(Instant.now()));
            dummyUser.setRole("ROLE_USER");
            dummyUser.setEnabled((short) 1);

            userRepository.save(dummyUser);
        }
    }

    @Transactional
    public void addAdminUser(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            // Save a dummy user to the database
            User dummyUser = new User();
            dummyUser.setUserId(1);
            dummyUser.setFirstName("John");
            dummyUser.setLastName("Doe");
            dummyUser.setEmail(email);
            // password
            dummyUser.setPassword(new BCryptPasswordEncoder().encode(password));
            dummyUser.setDtAdded(Timestamp.from(Instant.now()));
            dummyUser.setRole("ROLE_ADMIN");
            dummyUser.setEnabled((short) 1);
            userRepository.save(dummyUser);
        }
    }

    @Transactional
    public void clearUsers() {
        userRepository.deleteAll();
    }
}
