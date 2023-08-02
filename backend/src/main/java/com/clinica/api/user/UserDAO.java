package com.clinica.api.user;


import java.util.Optional;

public interface UserDAO {
    Optional<User> selectUserById(Integer id);
}
