package com.clinica.api.technique;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Integer> {
}
