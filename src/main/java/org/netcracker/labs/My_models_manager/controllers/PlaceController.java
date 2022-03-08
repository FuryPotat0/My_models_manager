package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.Entities.Place;
import org.netcracker.labs.My_models_manager.Entities.Room;
import org.netcracker.labs.My_models_manager.Services.PlaceService;
import org.netcracker.labs.My_models_manager.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/Places")
    public String getAll(Model model){
        List<Place> places = placeService.getAll();
        List<Room> rooms = roomService.getAll();
        model.addAttribute("placeNumber", places.size());
        model.addAttribute("placeList", places);
        model.addAttribute("roomList", rooms);
        return "places";
    }

    @RequestMapping("Places/delete/{id}")
    public String deletePlace(@PathVariable Long id){
        placeService.delete(id);
        return "redirect:/Places";
    }

    @PostMapping("/Places/add")
    public String addPlace(@ModelAttribute Place place){
        placeService.save(place);
        return "redirect:/Places";
    }
}

