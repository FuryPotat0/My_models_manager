package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> getAll(){
        return (List<Place>) placeRepository.findAll();
    }

    public void save(Place place){
        placeRepository.save(place);
    }

    public void delete(Long id){
        placeRepository.deleteById(id);
    }

    public Optional<Place> findById(Long id){
        return placeRepository.findById(id);
    }

    public List<Place> findAllByName(String name){
        List<Place> places = new ArrayList<>();
        for (Place place: placeRepository.findAll()){
            if(place.getName().toLowerCase().contains(name.toLowerCase())){
                places.add(place);
            }
        }
        return places;
    }
}
