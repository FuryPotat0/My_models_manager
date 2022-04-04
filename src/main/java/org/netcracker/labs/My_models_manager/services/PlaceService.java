package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public boolean delete(Long id){
        try {
            placeRepository.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    public Optional<Place> findById(Long id){
        return placeRepository.findById(id);
    }

    public List<Place> findAllByName(String name){
        return (List<Place>) placeRepository.findByNameContaining(name);
    }

    public int countByRoomId(Long id){
        int count = 0;
        for (Place place: placeRepository.findAll()){
            if(Objects.equals(place.getRoom().getId(), id)){
                count++;
            }
        }
        return count;
    }
}
