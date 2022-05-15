package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService implements ServiceInterface<Storage> {
    @Autowired
    private StorageRepository storageRepository;

    public List<Storage> getAll() {
        return (List<Storage>) storageRepository.findAll();
    }

    public void save(Storage entity) {
        entity.setId(System.currentTimeMillis());
        storageRepository.save(entity);
    }

    public void save(Storage entity, Long id) {
        entity.setId(id);
        storageRepository.save(entity);
    }

    public void update(Storage entity) {

    }

    public void delete(Long id) throws DataIntegrityViolationException {
        storageRepository.deleteById(id);
    }

    public List<Storage> findAllByName(String name) {
        return storageRepository.findByNameContaining(name);
    }

    public Optional<Storage> findById(Long id) {
        return storageRepository.findById(id);
    }

    public void deleteAll() {
        storageRepository.deleteAll();
    }
}

