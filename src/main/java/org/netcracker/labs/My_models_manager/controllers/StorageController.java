package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.entities.Place;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.services.PlaceService;
import org.netcracker.labs.My_models_manager.services.StorageService;
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
public class StorageController implements ControllerInterface<Storage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);
    @Autowired
    private StorageService storageService;
    @Autowired
    private PlaceService placeService;

    @GetMapping("/storages")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on StorageController page");
        List<Storage> storages;
        List<Place> places = placeService.getAll();
        if (name != null) {
            storages = storageService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else storages = storageService.getAll();

        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        model.addAttribute("highlighted", new FormCheckboxes());
        model.addAttribute("storageNumber", storages.size());
        model.addAttribute("storages", storages);
        model.addAttribute("places", places);
        return "Storage/storages";
    }

    @RequestMapping("/storages/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (storageService.findById(id).isPresent()) {
            try {
                storageService.delete(id);
                LOGGER.info("Storage with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("Storage {} with id={} wasn't deleted",
                        storageService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this storage, remove all links to this storage to delete it");
            }
        } else {
            LOGGER.warn("Storage with id={} don't exist", id);
        }
        return new ModelAndView("redirect:/storages", model);
    }

    @PostMapping("/storages/add")
    public ModelAndView addEntity(@ModelAttribute Storage storage, ModelMap model) {
        if (storage.getPlace() != null)
            if (!storage.getName().isEmpty()) {
                storageService.save(storage);
                LOGGER.info("Storage \"{}\" with id={} was added", storage.getName(), storage.getId());
                return new ModelAndView("redirect:/storages", model);
            } else LOGGER.warn("Storage wasn't added, name is empty");
        else LOGGER.warn("Storage wasn't added, Place is null");
        model.addAttribute("errorText", "Wrong input data");
        return new ModelAndView("redirect:/storages", model);
    }

    @GetMapping("/storages/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
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
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute Storage storage,
                                     ModelMap model) {
        LOGGER.info("User want edit Storage with id={}", id);
        if (storageService.findById(id).isEmpty()) {
            LOGGER.warn("Storage with id={} don't exist", id);
        } else {
            if (!storage.getName().isEmpty()) {
                storageService.update(storage);
                LOGGER.info("Storage {} with id={} was edited successfully", storage.getName(), id);
            } else {
                LOGGER.warn("Storage wasn't edited, name is empty");
                model.addAttribute("errorText", "Wrong input data");
            }
        }
        return new ModelAndView("redirect:/storages", model);
    }

    @PostMapping("/storages/deleteHighlighted")
    public ModelAndView deleteHighlighted(FormCheckboxes ids, ModelMap model) {
        try {
            storageService.deleteHighlighted(ids);
            LOGGER.info("Highlighted Storages were deleted");
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Highlighted Storages weren't deleted");
            model.addAttribute("errorText",
                    "Can't delete highlighted storages," +
                            " remove all links to storages to delete them");
        }
        return new ModelAndView("redirect:/storages", model);
    }
}

