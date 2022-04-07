package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.repositories.ModelStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelStatusService {
    @Autowired
    ModelStatusRepository modelStatusRepository;

    public List<ModelStatus> getAll() {
        return (List<ModelStatus>) modelStatusRepository.findAll();
    }

    public ModelStatus save(ModelStatus modelStatus) {
        return modelStatusRepository.save(modelStatus);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        modelStatusRepository.deleteById(id);
    }

    public List<ModelStatus> findAllByName(String name) {
        return (List<ModelStatus>) modelStatusRepository.findByNameContaining(name);
    }

    public Optional<ModelStatus> findById(Long id) {
        return modelStatusRepository.findById(id);
    }
}

