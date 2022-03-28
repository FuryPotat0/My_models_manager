package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.entities.Room;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);
    @Autowired
    private PlaceService placeService;
    @Autowired
    private RoomService roomService;
    private String errorText = "";

    @GetMapping("/places")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model) {
        LOGGER.info("User go on PlaceController page");
        List<Place> places;
        List<Room> rooms;
        if (name != null) {
            places = placeService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else {
            places = placeService.getAll();
        }
        rooms = roomService.getAll();
        model.addAttribute("placeNumber", places.size());
        model.addAttribute("placeList", places);
        model.addAttribute("roomList", rooms);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "places";
    }

    @RequestMapping("places/delete/{id}")
    public String deletePlace(@PathVariable Long id) {
        if (placeService.findById(id).isPresent()) {
            placeService.delete(id);
            LOGGER.info("Place {} with id={} was deleted", placeService.findById(id).get().getName(), id);
        } else {
            LOGGER.warn("Place with id={} don't exist", id);
        }
        return "redirect:/places";
    }

    @PostMapping("/places/add")
    public String addPlace(@ModelAttribute Place place) {
        if (place.getRoom() != null)
            if (!Objects.equals(place.getName(), "")) {
                placeService.save(place);
                LOGGER.info("Place {} with id={} was added", place.getName(), place.getId());
                return "redirect:/places";
            } else LOGGER.warn("Place wasn't added, name is empty");
        else LOGGER.warn("Place wasn't added, room is null");
        errorText = "Wrong input data";
        return "redirect:/places";
    }

    @GetMapping("/places/{id}")
    public String placeEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Place> place = placeService.findById(id);
        LOGGER.info("User want edit Place with id={}", id);
        if (place.isPresent()) {
            ArrayList<Place> res = new ArrayList<>();
            res.add(place.get());
            List<Room> rooms = roomService.getAll();
            model.addAttribute("place", res);
            model.addAttribute("roomList", rooms);
            LOGGER.info("Place {} with id={} will be edited", place.get().getName(), id);
            return "place-edit";
        } else {
            LOGGER.warn("No Place with id={}", id);
            return "redirect:/places";
        }
    }

    @PostMapping("/places/{id}")
    public String placeUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                              @RequestParam String description, @RequestParam String room) {
        Long roomId = Long.parseLong(room);
        LOGGER.info("User want edit Place with id={}", id);

        if (placeService.findById(id).isEmpty())
            LOGGER.warn("Place with id={} don't exist", id);
        else if (roomService.findById(roomId).isEmpty())
            LOGGER.warn("Room with id={} don't exist", roomId);
        else if (Objects.equals(name, ""))
            LOGGER.warn("Place name is empty");
        else {
            Place place = placeService.findById(id).get();
            place.setName(name);
            place.setDescription(description);
            place.setRoom(roomService.findById(roomId).get());
            placeService.save(place);
            LOGGER.info("Place {} with id={} was edited successfully", name, id);
            return "redirect:/places";
        }

        errorText = "Wrong input data";
        return "redirect:/places";
    }
}

