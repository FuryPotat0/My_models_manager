package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StorageRepository extends CrudRepository<Storage, Long> {
    Collection<Storage> findByNameContaining(String name);
}

