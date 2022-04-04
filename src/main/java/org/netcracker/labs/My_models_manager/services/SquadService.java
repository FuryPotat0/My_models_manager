package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Squad;
import org.netcracker.labs.My_models_manager.repositories.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SquadService {
    @Autowired
    private SquadRepository squadRepository;

    public List<Squad> getAll(){
        return (List<Squad>) squadRepository.findAll();
    }

    public void save(Squad squad){
        squadRepository.save(squad);
    }

    public boolean delete(Long id){
        try {
            squadRepository.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    public Optional<Squad> findById(Long id){
        return squadRepository.findById(id);
    }

    public List<Squad> findAllByName(String name){
        return (List<Squad>) squadRepository.findByNameContaining(name);
    }
}

