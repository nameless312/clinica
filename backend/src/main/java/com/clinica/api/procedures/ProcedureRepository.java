package com.clinica.api.procedures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
}
