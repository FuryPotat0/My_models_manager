package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Model;
import org.netcracker.labs.My_models_manager.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public List<Model> getAll(){
        return (List<Model>) modelRepository.findAll();
    }

    public void save(Model model){
        modelRepository.save(model);
    }

    public boolean delete(Long id){
        try {
            modelRepository.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    public List<Model> findAllByName(String name){
        return (List<Model>) modelRepository.findByNameContaining(name);
    }

    public Optional<Model> findById(Long id){
        return modelRepository.findById(id);
    }
}

