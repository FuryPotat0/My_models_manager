package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.StorageDao;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService implements ServiceInterface<Storage> {
    @Autowired
    private StorageDao storageDao;

    public List<Storage> getAll() {
        return storageDao.getAll();
    }

    public void save(Storage entity) {
        storageDao.save(entity, System.currentTimeMillis());
    }

    public void save(Storage entity, Long id) {
        storageDao.save(entity, id);
    }

    public void update(Storage entity) {
        storageDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        storageDao.delete(id);
    }

    public List<Storage> findAllByName(String name) {
        return storageDao.findAllByName(name);
    }

    public Optional<Storage> findById(Long id) {
        return Optional.of(storageDao.findById(id));
    }

    public void deleteAll() {
        storageDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes) throws DataIntegrityViolationException {
        storageDao.deleteHighlighted(formCheckboxes);
    }
}

