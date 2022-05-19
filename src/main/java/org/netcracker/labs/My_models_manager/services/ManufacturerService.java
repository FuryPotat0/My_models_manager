package org.netcracker.labs.My_models_manager.services;

import org.hibernate.HibernateException;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.ManufacturerDao;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService implements ServiceInterface<Manufacturer> {
    @Autowired
    private ManufacturerDao manufacturerDao;


    public List<Manufacturer> getAll() {
        return manufacturerDao.getAll();
    }

    public void save(Manufacturer entity) {
        manufacturerDao.save(entity, System.currentTimeMillis());
    }

    public void save(Manufacturer entity, Long id) {
        manufacturerDao.save(entity, id);
    }

    public void update(Manufacturer entity) {
        manufacturerDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        manufacturerDao.delete(id);
    }

    public List<Manufacturer> findAllByName(String name) {
        return manufacturerDao.findAllByName(name);
    }

    public Optional<Manufacturer> findById(Long id) {
        return Optional.of(manufacturerDao.findById(id));
    }

    public void deleteAll() {
        manufacturerDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes) throws DataIntegrityViolationException {
        manufacturerDao.deleteHighlighted(formCheckboxes);
    }
}

