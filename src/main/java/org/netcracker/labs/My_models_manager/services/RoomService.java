package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.daos.RoomDao;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
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
    private RoomDao roomDao;

    public List<Room> getAll() {
        return roomDao.getAll();
    }

    public void save(Room entity) {
        roomDao.save(entity, System.currentTimeMillis());
    }

    public void save(Room entity, Long id) {
        roomDao.save(entity, id);
    }

    public void update(Room entity){
        roomDao.update(entity);
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        roomDao.delete(id);
    }

    public List<Room> findAllByName(String name) {
        return roomDao.findAllByName(name);
    }

    public Optional<Room> findById(Long id) {
        return Optional.of(roomDao.findById(id));
    }

    public void deleteAll() {
        roomDao.deleteAll();
    }

    public void deleteHighlighted(FormCheckboxes formCheckboxes) throws DataIntegrityViolationException  {
        roomDao.deleteHighlighted(formCheckboxes);
    }
}
