package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.entities.Room;
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
public class RoomController implements ControllerInterface<Room> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on RoomController page");
        List<Room> rooms;
        if (name != null) {
            rooms = roomService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else rooms = roomService.getAll();

        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        model.addAttribute("roomNumber", rooms.size());
        model.addAttribute("highlighted", new FormCheckboxes());
        model.addAttribute("roomList", rooms);
        return "Room/rooms";
    }

    @RequestMapping("/rooms/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (roomService.findById(id).isPresent()) {
            try {
                roomService.delete(id);
                LOGGER.info("Room with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.warn("Room {} with id={} wasn't deleted", roomService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this room, remove all links to this room to delete it");
            }
        } else LOGGER.warn("Room with id={} don't exist", id);
        return new ModelAndView("redirect:/rooms", model);
    }

    @PostMapping("/rooms/add")
    public ModelAndView addEntity(@ModelAttribute Room room, ModelMap model) {
        if (!room.getName().isEmpty()) {
            roomService.save(room);
            LOGGER.info("Room {} with id={} was added", room.getName(), room.getId());
            return new ModelAndView("redirect:/rooms", model);
        } else LOGGER.warn("Room wasn't added, name is empty");
        model.addAttribute("errorText", "Wrong input data");
        return new ModelAndView("redirect:/rooms", model);
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
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute Room room, ModelMap model) {
        LOGGER.info("User want edit Room with id={}", id);
        if (roomService.findById(id).isEmpty())
            LOGGER.warn("Room with id={} don't exist", id);
        else if (!room.getName().isEmpty()) {
            roomService.update(room);
            LOGGER.info("Room {} with id={} was edited successfully", room.getName(), id);
        } else{
            LOGGER.warn("Room wasn't edited, name is empty");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/rooms", model);
    }

    @PostMapping("/rooms/deleteHighlighted")
    public ModelAndView deleteHighlighted(@ModelAttribute("highlighted") FormCheckboxes ids, ModelMap model) {
        try {
            roomService.deleteHighlighted(ids);
            LOGGER.info("Highlighted Rooms were deleted");
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Highlighted Rooms weren't deleted");
            model.addAttribute("errorText",
                    "Can't delete highlighted rooms, remove all links to rooms to delete them");
        }
        return new ModelAndView("redirect:/manufacturers", model);
    }
}

