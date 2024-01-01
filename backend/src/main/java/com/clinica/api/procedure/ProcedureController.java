package com.clinica.api.procedure;

import com.clinica.api.procedure.input.NewProcedure;
import com.clinica.api.procedure.input.UpdateProcedure;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/procedure")
public class ProcedureController {
    private final ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping
    public ResponseEntity<List<ProcedureDTO>> getProcedures(Authentication authentication) {
        return ResponseEntity.ok().body(procedureService.getAllProcedures());
    }

    @PostMapping
    public ResponseEntity<Void> insertProcedure(Authentication authentication,
                                                @Valid @RequestBody NewProcedure newProcedure) {
        procedureService.insertProcedure(newProcedure);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateProcedure(Authentication authentication,
                                                @Valid @RequestBody UpdateProcedure updateProcedure) {
        procedureService.updateProcedure(updateProcedure);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
