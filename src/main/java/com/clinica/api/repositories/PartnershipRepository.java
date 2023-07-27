package com.clinica.api.repositories;

import com.clinica.api.entities.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
}
