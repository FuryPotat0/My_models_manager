package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long> {
    Collection<Manufacturer> findByNameContaining(String name);
}

