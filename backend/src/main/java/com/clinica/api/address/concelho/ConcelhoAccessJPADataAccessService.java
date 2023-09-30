package com.clinica.api.address.concelho;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ConcelhoJPA")
public class ConcelhoAccessJPADataAccessService implements ConcelhoDAO {
    private final ConcelhoRepository concelhoRepository;

    public ConcelhoAccessJPADataAccessService(ConcelhoRepository concelhoRepository) {
        this.concelhoRepository = concelhoRepository;
    }
    @Override
    public Optional<Concelho> selectConcelhoById(Integer id) {
        return concelhoRepository.findById(id);
    }

    @Override
    public List<Concelho> selectAllConcelhos() {
        return concelhoRepository.findAll();
    }


}
