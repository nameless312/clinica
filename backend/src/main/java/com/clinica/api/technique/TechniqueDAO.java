package com.clinica.api.technique;

import java.util.List;
import java.util.Optional;

public interface TechniqueDAO {
    Optional<Technique> selectTechniqueById(Integer id);
    List<Technique> selectTechniques();
    Technique insertTechnique(Technique technique);
    void updateTechnique(Technique technique);
}
