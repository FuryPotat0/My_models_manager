package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.Entities.Room;
import org.netcracker.labs.My_models_manager.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/Rooms")
    public String getAll(Model model){
        List<Room> rooms = roomService.getAll();
        model.addAttribute("roomNumber", rooms.size());
        model.addAttribute("roomList", rooms);
        return "rooms";
    }

    @RequestMapping("/Rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        roomService.delete(id);
        return "redirect:/Rooms";
    }

    @PostMapping("/Rooms/add")
    public String addManufacturer(@ModelAttribute Room room){
        roomService.save(room);
        return "redirect:/Rooms";
    }
}

