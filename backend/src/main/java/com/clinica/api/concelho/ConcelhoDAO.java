package com.clinica.api.concelho;


import java.util.Optional;

public interface ConcelhoDAO {
    Optional<Concelho> selectConcelhoById(Integer id);
}
