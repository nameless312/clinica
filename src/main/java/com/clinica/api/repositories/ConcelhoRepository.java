package com.clinica.api.repositories;

import com.clinica.api.entities.Concelho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcelhoRepository extends JpaRepository<Concelho, Integer> {
}
