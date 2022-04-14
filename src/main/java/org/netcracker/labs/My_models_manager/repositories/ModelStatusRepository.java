package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelStatusRepository extends CrudRepository<ModelStatus, Long> {
    List<ModelStatus> findByNameContaining(String name);
}
