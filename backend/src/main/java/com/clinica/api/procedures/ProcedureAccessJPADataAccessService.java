package com.clinica.api.procedures;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ProcedureJPA")
public class ProcedureAccessJPADataAccessService implements ProcedureDAO {

    private final ProcedureRepository procedureRepository;

    public ProcedureAccessJPADataAccessService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;

    }
    @Override
    public Optional<Procedure> selectProcedureById(Integer id) {
        return procedureRepository.findById(id);
    }

    @Override
    public List<Procedure> selectProcedures() {
        return procedureRepository.findAll();
    }

    @Override
    public Procedure insertProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    @Override
    public void updateProcedure(Procedure procedure) {
        procedureRepository.save(procedure);
    }
}
