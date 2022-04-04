package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAll(){
        return (List<Manufacturer>) manufacturerRepository.findAll();
    }

    public void save(Manufacturer manufacturer){
        manufacturerRepository.save(manufacturer);
    }

    public boolean delete(Long id){
        try {
            manufacturerRepository.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    public List<Manufacturer> findAllByName(String name){
        return (List<Manufacturer>) manufacturerRepository.findByNameContaining(name);
    }

    public Optional<Manufacturer> findById(Long id){
        return manufacturerRepository.findById(id);
    }
}

