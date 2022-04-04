package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    Collection<Place> findByNameContaining(String name);
}
