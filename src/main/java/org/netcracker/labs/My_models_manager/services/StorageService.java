package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public List<Storage> getAll(){
        return (List<Storage>) storageRepository.findAll();
    }

    public void save(Storage storage){
        storageRepository.save(storage);
    }

    public boolean delete(Long id){
        try {
            storageRepository.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    public Optional<Storage> findById(Long id){
        return storageRepository.findById(id);
    }

    public List<Storage> findAllByName(String name){
        return (List<Storage>) storageRepository.findByNameContaining(name);
    }
}

