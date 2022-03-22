package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.entities.Room;
import org.netcracker.labs.My_models_manager.exceptions.NoSuitableRoomException;
import org.netcracker.labs.My_models_manager.services.PlaceService;
import org.netcracker.labs.My_models_manager.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @Autowired
    private RoomService roomService;

    private String errorText = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);

    @GetMapping("/places")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("im here");
        List<Place> places;
        List<Room> rooms;
        if (name != null){
            places = placeService.findAllByName(name);
            rooms = roomService.getAll();
            LOGGER.info(String.valueOf(places.size()));
            model.addAttribute("placeNumber", places.size());
            model.addAttribute("placeList", places);
            model.addAttribute("roomList", rooms);
        }
        else {
            places = placeService.getAll();
            rooms = roomService.getAll();
            model.addAttribute("placeNumber", places.size());
            model.addAttribute("placeList", places);
            model.addAttribute("roomList", rooms);
            model.addAttribute("errorText", errorText);
        }
        errorText = "";
        return "places";
    }

    @RequestMapping("places/delete/{id}")
    public String deletePlace(@PathVariable Long id){
        placeService.delete(id);
        return "redirect:/places";
    }

    @PostMapping("/places/add")
    public String addPlace(@ModelAttribute Place place){
        try {
            if(place.getRoom() != null)
                if (!Objects.equals(place.getName(), ""))
                    placeService.save(place);
                else throw new IllegalArgumentException("");
            else throw new NoSuitableRoomException("");
        }
        catch (NoSuitableRoomException | IllegalArgumentException e) {
            errorText = "Wrong input data";
            e.printStackTrace();
        }
        return "redirect:/places";
    }

    @GetMapping("/place/edit/{id}")
    public String placeEdit(@PathVariable(value = "id") Long id, Model model){
        Optional<Place> place = placeService.findById(id);
        ArrayList<Place> res = new ArrayList<>();
        place.ifPresent(res::add);
        List<Room> rooms = roomService.getAll();
        model.addAttribute("place", res);
        model.addAttribute("roomList", rooms);
        return "place-edit";
    }

    @PostMapping("/place/edit/{id}")
    public String placeUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,  @RequestParam String description, @RequestParam String room){
        Long roomId = Long.parseLong(room);
        try {
            Place place = placeService.findById(id).orElseThrow();
            if(roomService.isExist(roomId))
                if (!Objects.equals(name, "")){
                    place.setName(name);
                    place.setDescription(description);
                    place.setRoom(roomService.findById(roomId).orElseThrow());
                    placeService.save(place);
                }
                else throw new IllegalArgumentException("");
            else throw new NoSuitableRoomException("");
        }
        catch (NoSuitableRoomException | IllegalArgumentException e) {
            errorText = "Wrong input data";
            e.printStackTrace();
        }
        return "redirect:/places";
    }
}

