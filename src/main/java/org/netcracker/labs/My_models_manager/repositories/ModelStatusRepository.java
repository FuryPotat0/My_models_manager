package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelStatusRepository extends CrudRepository<Status, Long> {
    List<Status> findByNameContaining(String name);
}
