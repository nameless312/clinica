package com.clinica.api.procedures;

import java.util.List;
import java.util.Optional;

public interface ProcedureDAO {
    Optional<Procedure> selectProcedureById(Integer id);
    List<Procedure> selectProcedures();
    Procedure insertProcedure(Procedure partnership);
    void updateProcedure(Procedure partnership);
}
