package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.entities.Room;
import org.netcracker.labs.My_models_manager.services.PlaceService;
import org.netcracker.labs.My_models_manager.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PlaceController implements ControllerInterface<Place> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);
    @Autowired
    private PlaceService placeService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/places")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on PlaceController page");
        List<Place> places;
        List<Room> rooms = roomService.getAll();
        if (name != null) {
            places = placeService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else places = placeService.getAll();
        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        model.addAttribute("placeNumber", places.size());
        model.addAttribute("placeList", places);
        model.addAttribute("roomList", rooms);
        return "Place/places";
    }

    @RequestMapping("/places/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (placeService.findById(id).isPresent()) {
            try {
                placeService.delete(id);
                LOGGER.info("Place with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("Place {} with id={} wasn't deleted",
                        placeService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this place, remove all links to this place to delete it");
            }
        } else LOGGER.warn("Place with id={} don't exist", id);
        return new ModelAndView("redirect:/places", model);
    }

    @PostMapping("/places/add")
    public ModelAndView addEntity(@ModelAttribute Place place, ModelMap model) {
        if (place.getRoom() != null)
            if (!place.getName().isEmpty()) {
                placeService.save(place);
                LOGGER.info("Place \"{}\" with id={} was added", place.getName(), place.getId());
                return new ModelAndView("redirect:/places", model);
            } else LOGGER.warn("Place wasn't added, name is empty");
        else LOGGER.warn("Place wasn't added, room is null");
        model.addAttribute("errorText", "Wrong input data");
        return new ModelAndView("redirect:/places", model);
    }

    @GetMapping("/places/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
        Optional<Place> place = placeService.findById(id);
        LOGGER.info("User want visit Place with id={}", id);
        if (place.isPresent()) {
            ArrayList<Place> res = new ArrayList<>();
            res.add(place.get());
            List<Room> rooms = roomService.getAll();
            model.addAttribute("place", res);
            model.addAttribute("roomList", rooms);
            LOGGER.info("Place \"{}\" with id={} will be edited", place.get().getName(), id);
            return "Place/place-edit";
        } else {
            LOGGER.warn("No Place with id={}", id);
            return "redirect:/places";
        }
    }

    @PostMapping("/places/{id}")
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute Place place, ModelMap model) {
        LOGGER.info("User want edit Place with id={}", id);

        if (placeService.findById(id).isEmpty())
            LOGGER.warn("Place with id={} don't exist", id);
        else {
            if (!place.getName().isEmpty()) {
                placeService.save(place);
                LOGGER.info("Place {} with id={} was edited successfully", place.getName(), id);
            } else{
                LOGGER.warn("Place wasn't edited, name is empty");
                model.addAttribute("errorText", "Wrong input data");
            }
        }
        return new ModelAndView("redirect:/places", model);
    }
}

