package com.clinica.api.technique;

import com.clinica.api.technique.input.NewTechniqueInput;
import com.clinica.api.technique.input.UpdateTechnique;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/technique")
public class TechniqueController {
    private final TechniqueService techniqueService;

    public TechniqueController(TechniqueService techniqueService) {
        this.techniqueService = techniqueService;
    }

    @GetMapping
    public ResponseEntity<List<TechniqueDTO>> getTechniques(Authentication authentication) {
        return ResponseEntity.ok().body(techniqueService.getAllTechniques());
    }

    @PostMapping
    public ResponseEntity<Void> insertTechnique(Authentication authentication,
                                                @Valid @RequestBody NewTechniqueInput newTechniqueInput) {
        techniqueService.insertTechnique(newTechniqueInput);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateTechnique(Authentication authentication,
                                                @Valid @RequestBody UpdateTechnique updateTechnique) {
        techniqueService.updateTechnique(updateTechnique);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
