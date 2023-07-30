package com.clinica.api.partnership;

import com.clinica.api.partnership.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
}
