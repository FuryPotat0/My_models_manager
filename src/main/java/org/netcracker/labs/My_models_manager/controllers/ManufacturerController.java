package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.services.ManufacturerService;
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
public class ManufacturerController implements ControllerInterface<Manufacturer> {
    private static final Logger LOGGER = LogManager.getLogger(ManufacturerController.class);
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/manufacturers")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on ManufacturerController page");
        List<Manufacturer> manufacturers;
        if (name != null) {
            manufacturers = manufacturerService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else manufacturers = manufacturerService.getAll();

        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        model.addAttribute("manufacturerList", manufacturers);
        model.addAttribute("manufacturersSize", manufacturers.size());
        return "Manufacturer/manufacturers";
    }

    @RequestMapping("/manufacturers/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (manufacturerService.findById(id).isPresent()) {
            try {
                manufacturerService.delete(id);
                LOGGER.info("Manufacturer with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("Manufacturer {} with id={} wasn't deleted",
                        manufacturerService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this manufacturer, remove all links to this manufacturer to delete it");
            }
        } else LOGGER.warn("Manufacturer with id={} don't exist", id);
        return new ModelAndView("redirect:/manufacturers", model);
    }

    @PostMapping("/manufacturers/add")
    public ModelAndView addEntity(@ModelAttribute Manufacturer manufacturer, ModelMap model) {
        if (!manufacturer.getName().isEmpty()) {
            manufacturerService.save(manufacturer);
            LOGGER.info("Manufacturer {} with id={} was added", manufacturer.getName(), manufacturer.getId());
        } else {
            LOGGER.warn("Manufacturer wasn't added, name is empty");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/manufacturers", model);
    }

    @GetMapping("/manufacturers/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        LOGGER.info("User want visit Manufacturer with id={}", id);
        if (manufacturer.isPresent()) {
            ArrayList<Manufacturer> res = new ArrayList<>();
            res.add(manufacturer.get());
            model.addAttribute("manufacturer", res);
            LOGGER.info("Manufacturer {} with id={} will be edited", manufacturer.get().getName(), id);
            return "Manufacturer/manufacturer-edit";
        } else {
            LOGGER.warn("No Manufacturer with id={}", id);
            return "redirect:/manufacturers";
        }
    }

    @PostMapping("/manufacturers/{id}")
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute Manufacturer manufacturer,
                                     ModelMap model) {
        LOGGER.info("User want edit Manufacturer with id={}", id);
        if (manufacturerService.findById(id).isEmpty())
            LOGGER.warn("Manufacturer with id={} don't exist", id);
        else if (!manufacturer.getName().isEmpty()) {
            manufacturerService.save(manufacturer);
            LOGGER.info("Manufacturer {} with id={} was edited successfully", manufacturer.getName(), id);
        } else {
            LOGGER.warn("Manufacturer wasn't edited, name is empty");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/manufacturers", model);
    }
}

