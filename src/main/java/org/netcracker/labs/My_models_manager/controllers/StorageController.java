package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.services.PlaceService;
import org.netcracker.labs.My_models_manager.services.StorageService;
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
public class StorageController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private PlaceService placeService;

    private String errorText = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

    @GetMapping("/storages")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model) {
        LOGGER.info("User go on StorageController page");
        List<Storage> storages;
        List<Place> places;
        if (name != null) {
            storages = storageService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else storages = storageService.getAll();
        places = placeService.getAll();
        model.addAttribute("storageNumber", storages.size());
        model.addAttribute("storages", storages);
        model.addAttribute("places", places);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "Storage/storages";
    }

    @RequestMapping("/storages/delete/{id}")
    public String deleteStorage(@PathVariable Long id) {
        if (storageService.findById(id).isPresent()) {
            storageService.delete(id);
            LOGGER.info("Storage \"{}\" with id={} was deleted", storageService.findById(id).get().getName(), id);
        } else {
            LOGGER.warn("Storage with id={} don't exist", id);
        }
        return "redirect:/storages";
    }

    @PostMapping("/storages/add")
    public String addStorage(@ModelAttribute Storage storage) {
        if (storage.getPlace() != null)
            if (!storage.getName().isEmpty()) {
                storageService.save(storage);
                LOGGER.info("Storage \"{}\" with id={} was added", storage.getName(), storage.getId());
                return "redirect:/storages";
            } else LOGGER.warn("Storage wasn't added, name is empty");
        else LOGGER.warn("Storage wasn't added, Place is null");
        errorText = "Wrong input data";
        return "redirect:/storages";
    }

    @GetMapping("/storages/{id}")
    public String storageEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Storage> storage = storageService.findById(id);
        LOGGER.info("User want visit Storage with id={}", id);
        if (storage.isPresent()) {
            ArrayList<Storage> res = new ArrayList<>();
            res.add(storage.get());
            List<Place> places = placeService.getAll();
            model.addAttribute("storage", res);
            model.addAttribute("places", places);
            LOGGER.info("Storage \"{}\" with id={} will be edited", storage.get().getName(), id);
            return "Storage/storage-edit";
        } else {
            LOGGER.warn("No Storage with id={}", id);
            return "redirect:/storages";
        }
    }

    @PostMapping("/storages/{id}")
    public String storagesUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                              @RequestParam String description, @RequestParam String place) {
        LOGGER.info("User want edit Storage with id={}", id);
        Long placeId = Long.parseLong(place);

        if (storageService.findById(id).isEmpty())
            LOGGER.warn("Storage with id={} don't exist", id);
        else if (placeService.findById(placeId).isEmpty())
            LOGGER.warn("Place with id={} don't exist", placeId);
        else if (Objects.equals(name, ""))
            LOGGER.warn("Storage name is empty");
        else {
            Storage storage = storageService.findById(id).get();
            storage.setName(name);
            storage.setDescription(description);
            storage.setPlace(placeService.findById(placeId).get());
            storageService.save(storage);
            LOGGER.info("Storage {} with id={} was edited successfully", name, id);
            return "redirect:/storages";
        }
        errorText = "Wrong input data";
        return "redirect:/storages";
    }
}

