package com.clinica.api.technique;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TechniqueJPA")
public class TechniqueAccessJPADataAccessService implements TechniqueDAO {

    private final TechniqueRepository techniqueRepository;

    public TechniqueAccessJPADataAccessService(TechniqueRepository techniqueRepository) {
        this.techniqueRepository = techniqueRepository;

    }
    @Override
    public Optional<Technique> selectTechniqueById(Integer id) {
        return techniqueRepository.findById(id);
    }

    @Override
    public List<Technique> selectTechniques() {
        return techniqueRepository.findAll();
    }

    @Override
    public Technique insertTechnique(Technique technique) {
        return techniqueRepository.save(technique);
    }

    @Override
    public void updateTechnique(Technique technique) {
        techniqueRepository.save(technique);
    }
}
