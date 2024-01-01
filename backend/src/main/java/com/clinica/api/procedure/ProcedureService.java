package com.clinica.api.procedure;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.procedure.input.NewProcedure;
import com.clinica.api.procedure.input.UpdateProcedure;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcedureService {
    private final ProcedureDAO procedureDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public ProcedureService(@Qualifier(value = "ProcedureJPA") ProcedureDAO procedureDAO,
                            @Qualifier(value = "UserJPA") UserDAO userDAO,
                            Clock clock) {
        this.procedureDAO = procedureDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<ProcedureDTO> getAllProcedures() {
        List<Procedure> partnerships = procedureDAO.selectProcedures();
        if (partnerships.isEmpty()) {
            return new ArrayList<>();
        }
        return partnerships.stream().map(Procedure::toDTO).collect(Collectors.toList());
    }

    public void insertProcedure(NewProcedure newProcedure) {
        Integer userId = newProcedure.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Procedure procedure = new Procedure();

        procedure.setUser(user);
        procedure.setProcedureDesc(newProcedure.procedureDesc());
        procedureDAO.insertProcedure(procedure);
    }

    public void updateProcedure(UpdateProcedure updateProcedure) {
        Integer procedureId = updateProcedure.procedureId();

        Procedure procedure = procedureDAO.selectProcedureById(procedureId).
                orElseThrow(
                        () -> new ResourceNotFoundException("procedure with id [%s] not found".formatted(procedureId))
                );

        procedure.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
        procedure.setProcedureDesc(updateProcedure.procedureDesc());

        procedureDAO.updateProcedure(procedure);
    }
}
