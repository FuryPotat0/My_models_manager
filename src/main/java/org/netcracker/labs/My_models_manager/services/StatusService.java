package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.StatusDao;
import org.netcracker.labs.My_models_manager.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService implements ServiceInterface<Status> {
    @Autowired
    private StatusDao statusDao;

    public List<Status> getAll() {
        return statusDao.getAll();
    }

    public void save(Status entity) {
        statusDao.save(entity, System.currentTimeMillis());
    }

    public void save(Status entity, Long id) {
        statusDao.save(entity, id);
    }

    public void update(Status entity) {
        statusDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        statusDao.delete(id);
    }

    public List<Status> findAllByName(String name) {
        return statusDao.findAllByName(name);
    }

    public Optional<Status> findById(Long id) {
        return Optional.of(statusDao.findById(id));
    }

    public void deleteAll() {
        statusDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes)  throws DataIntegrityViolationException  {
        statusDao.deleteHighlighted(formCheckboxes);
    }
}

