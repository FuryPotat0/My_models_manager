package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Model;
import org.netcracker.labs.My_models_manager.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService implements ServiceInterface<Model> {
    @Autowired
    private ModelRepository modelRepository;

    public List<Model> getAll() {
        return (List<Model>) modelRepository.findAll();
    }

    public void save(Model entity) {
        entity.setId(System.currentTimeMillis());
        modelRepository.save(entity);
    }

    public void save(Model entity, Long id) {
        entity.setId(id);
        modelRepository.save(entity);
    }

    public void update(Model entity) {

    }

    public void delete(Long id) throws DataIntegrityViolationException {
        modelRepository.deleteById(id);
    }

    public List<Model> findAllByName(String name) {
        return modelRepository.findByNameContaining(name);
    }

    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }

    public void deleteAll() {
        modelRepository.deleteAll();
    }
}

