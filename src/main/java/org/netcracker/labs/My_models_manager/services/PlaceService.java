package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.PlaceDao;
import org.netcracker.labs.My_models_manager.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService implements ServiceInterface<Place>{
    @Autowired
    private PlaceDao placeDao;

    public List<Place> getAll() {
        return (List<Place>) placeDao.getAll();
    }

    public void save(Place entity) {
        placeDao.save(entity, System.currentTimeMillis());
    }

    public void save(Place entity, Long id) {
        placeDao.save(entity, id);
    }

    public void update(Place entity) {
        placeDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        placeDao.delete(id);
    }

    public List<Place> findAllByName(String name) {
        return placeDao.findAllByName(name);
    }

    public Optional<Place> findById(Long id) {
        return Optional.of(placeDao.findById(id));
    }

    public void deleteAll() {
        placeDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes) {
        placeDao.deleteHighlighted(formCheckboxes);
    }
}
