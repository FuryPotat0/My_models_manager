package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelRepository extends CrudRepository<Model, Long> {
    List<Model> findByNameContaining(String name);
}
