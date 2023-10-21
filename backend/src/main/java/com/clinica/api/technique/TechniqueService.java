package com.clinica.api.technique;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.technique.input.NewTechniqueInput;
import com.clinica.api.technique.input.UpdateTechnique;
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
public class TechniqueService {
    private final TechniqueDAO techniqueDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public TechniqueService(@Qualifier(value = "TechniqueJPA") TechniqueDAO techniqueDAO,
                            @Qualifier(value = "UserJPA") UserDAO userDAO,
                            Clock clock) {
        this.techniqueDAO = techniqueDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<TechniqueDTO> getAllTechniques() {
        List<Technique> techniques = techniqueDAO.selectTechniques();
        if (techniques.isEmpty()) {
            return new ArrayList<>();
        }
        return techniques.stream().map(Technique::toDTO).collect(Collectors.toList());
    }

    public void insertTechnique(NewTechniqueInput newTechniqueInput) {
        Integer userId = newTechniqueInput.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Technique technique = new Technique();

        technique.setUser(user);
        technique.setTechnique(newTechniqueInput.technique());
        techniqueDAO.insertTechnique(technique);
    }

    public void updateTechnique(UpdateTechnique updateTechnique) {
        Integer techniqueId = updateTechnique.techniqueId();

        Technique technique = techniqueDAO.selectTechniqueById(techniqueId).
                orElseThrow(
                        () -> new ResourceNotFoundException("technique with id [%s] not found".formatted(techniqueId))
                );

        technique.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
        technique.setTechnique(updateTechnique.technique());

        techniqueDAO.updateTechnique(technique);
    }
}
