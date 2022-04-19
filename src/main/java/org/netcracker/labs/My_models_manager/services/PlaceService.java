package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService implements ServiceInterface<Place>{
    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> getAll() {
        return (List<Place>) placeRepository.findAll();
    }

    public void save(Place entity) {
        placeRepository.save(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        placeRepository.deleteById(id);
    }

    public List<Place> findAllByName(String name) {
        return (List<Place>) placeRepository.findByNameContaining(name);
    }

    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }
}
