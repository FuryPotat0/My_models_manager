package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.ModelDao;
import org.netcracker.labs.My_models_manager.entities.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService implements ServiceInterface<Model> {
    @Autowired
    private ModelDao modelDao;

    public List<Model> getAll() {
        return (List<Model>) modelDao.getAll();
    }

    public void save(Model entity) {
        modelDao.save(entity, System.currentTimeMillis());
    }

    public void save(Model entity, Long id) {
        modelDao.save(entity, id);
    }

    public void update(Model entity) {
        modelDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        modelDao.delete(id);
    }

    public List<Model> findAllByName(String name) {
        return modelDao.findAllByName(name);
    }

    public Optional<Model> findById(Long id) {
        return Optional.of(modelDao.findById(id));
    }

    public void deleteAll() {
        modelDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes) throws DataIntegrityViolationException {
        modelDao.deleteHighlighted(formCheckboxes);
    }
}

