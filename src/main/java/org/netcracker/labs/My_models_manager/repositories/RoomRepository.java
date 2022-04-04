package org.netcracker.labs.My_models_manager.repositories;

import org.netcracker.labs.My_models_manager.entities.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RoomRepository extends CrudRepository<Room, Long> {
    Collection<Room> findByNameContaining(String name);
}
