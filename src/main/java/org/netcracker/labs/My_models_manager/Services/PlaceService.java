package org.netcracker.labs.My_models_manager.Services;

import org.netcracker.labs.My_models_manager.Entities.Place;
import org.netcracker.labs.My_models_manager.Repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> getAll(){
        return (List<Place>) placeRepository.findAll();
    }

    public Place save(Place manufacturer){
        return placeRepository.save(manufacturer);
    }

    public void delete(Long id){
        placeRepository.deleteById(id);
    }
}

