package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Room;
import org.netcracker.labs.My_models_manager.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController implements ControllerInterface<Room> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
    private String errorText = "";

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("User go on RoomController page");
        List<Room> rooms;
        if (name != null) {
            rooms = roomService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else {
            rooms = roomService.getAll();
        }
        model.addAttribute("roomNumber", rooms.size());
        model.addAttribute("roomList", rooms);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "Room/rooms";
    }

    @RequestMapping("/rooms/delete/{id}")
    public String deleteEntity(@PathVariable Long id){
        if (roomService.findById(id).isPresent()) {
            try {
                roomService.delete(id);
                LOGGER.info("Room with id={} was deleted", id);
            }
            catch (DataIntegrityViolationException e) {
                LOGGER.warn("Room {} with id={} wasn't deleted", roomService.findById(id).get().getName(), id);
                errorText = "Can't delete this room, remove all links to this room to delete it";
            }
        }
        else LOGGER.warn("Room with id={} don't exist", id);
        return "redirect:/rooms";
    }

    @PostMapping("/rooms/add")
    public String addEntity(@ModelAttribute Room room){
        roomService.save(room);
        if (!room.getName().isEmpty()){
            roomService.save(room);
            LOGGER.info("Room {} with id={} was added", room.getName(), room.getId());
            return "redirect:/rooms";
        }
        else LOGGER.warn("Room wasn't added, name is empty");
        errorText = "Wrong input data";
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
        Optional<Room> room = roomService.findById(id);
        LOGGER.info("User want visit Room with id={}", id);
        if (room.isPresent()) {
            ArrayList<Room> res = new ArrayList<>();
            res.add(room.get());
            model.addAttribute("room", res);
            LOGGER.info("Room {} with id={} will be edited", room.get().getName(), id);
            return "Room/room-edit";
        } else {
            LOGGER.warn("No Room with id={}", id);
            return "redirect:/rooms";
        }
    }

    @PostMapping("/rooms/{id}")
    public String updateEntity(@PathVariable(value = "id") Long id,  @ModelAttribute Room room) {
        LOGGER.info("User want edit Room with id={}", id);
        if (roomService.findById(id).isEmpty())
            LOGGER.warn("Room with id={} don't exist", id);
        else
            if (!room.getName().isEmpty()){
                roomService.save(room);
                LOGGER.info("Room {} with id={} was edited successfully", room.getName(), id);
            }
            else errorText = "Wrong input data";
        return "redirect:/rooms";
    }
}

