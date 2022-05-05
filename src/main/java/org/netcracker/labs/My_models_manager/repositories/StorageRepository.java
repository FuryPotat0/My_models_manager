package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageRepository extends CrudRepository<Storage, Long> {
    List<Storage> findByNameContaining(String name);
}

