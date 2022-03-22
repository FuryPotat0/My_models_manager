package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Room;
import org.netcracker.labs.My_models_manager.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAll(){
        return (List<Room>) roomRepository.findAll();
    }

    public void save(Room room){
        roomRepository.save(room);
    }

    public void delete(Long id){
        roomRepository.deleteById(id);
    }

    public boolean isExist(Long id){
        return roomRepository.findById(id).isPresent();
    }

    public Optional<Room> findById(Long id){
        return roomRepository.findById(id);
    }

    public List<Room> findAllByName(String name){
        List<Room> rooms = new ArrayList<>();
        for (Room room: roomRepository.findAll()){
            if(room.getName().toLowerCase().contains(name.toLowerCase())){
                rooms.add(room);
            }
        }
        return rooms;
    }
}
