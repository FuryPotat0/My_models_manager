package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Room;
import org.netcracker.labs.My_models_manager.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements ServiceInterface<Room>{
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAll() {
        return (List<Room>) roomRepository.findAll();
    }

    public void save(Room entity) {
        roomRepository.save(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        roomRepository.deleteById(id);
    }

    public List<Room> findAllByName(String name) {
        return (List<Room>) roomRepository.findByNameContaining(name);
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }
}
