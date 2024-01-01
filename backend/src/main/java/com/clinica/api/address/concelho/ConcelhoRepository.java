package com.clinica.api.address.concelho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcelhoRepository extends JpaRepository<Concelho, Integer> {
}
