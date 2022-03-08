package org.netcracker.labs.My_models_manager.Services;

import org.netcracker.labs.My_models_manager.Entities.Room;
import org.netcracker.labs.My_models_manager.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAll(){
        return (List<Room>) roomRepository.findAll();
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public void delete(Long id){
        roomRepository.deleteById(id);
    }
}

