package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.ModelStatusDao;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelStatusService implements ServiceInterface<ModelStatus> {
    @Autowired
    private ModelStatusDao modelStatusDao;

    public List<ModelStatus> getAll() {
        return modelStatusDao.getAll();
    }

    public void save(ModelStatus entity) {
        modelStatusDao.save(entity, System.currentTimeMillis());
    }

    public void save(ModelStatus entity, Long id) {
        modelStatusDao.save(entity, id);
    }

    public void update(ModelStatus entity) {
        modelStatusDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        modelStatusDao.delete(id);
    }

    public List<ModelStatus> findAllByName(String name) {
        return modelStatusDao.findAllByName(name);
    }

    public Optional<ModelStatus> findById(Long id) {
        return Optional.of(modelStatusDao.findById(id));
    }

    public void deleteAll() {
        modelStatusDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes)  throws DataIntegrityViolationException  {
        modelStatusDao.deleteHighlighted(formCheckboxes);
    }
}

