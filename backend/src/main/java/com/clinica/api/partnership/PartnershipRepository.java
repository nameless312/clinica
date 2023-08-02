package com.clinica.api.partnership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
}
