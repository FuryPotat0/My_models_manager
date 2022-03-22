package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Squad;
import org.netcracker.labs.My_models_manager.repositories.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void delete(Long id){
        squadRepository.deleteById(id);
    }

    public Optional<Squad> findById(Long id){
        return squadRepository.findById(id);
    }

    public List<Squad> findAllByName(String name){
        List<Squad> squads = new ArrayList<>();
        for (Squad squad: squadRepository.findAll()){
            if(squad.getName().toLowerCase().contains(name.toLowerCase())){
                squads.add(squad);
            }
        }
        return squads;
    }
}

