package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Squad;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface SquadRepository extends CrudRepository<Squad, Long> {
    Collection<Squad> findByNameContaining(String name);
}
