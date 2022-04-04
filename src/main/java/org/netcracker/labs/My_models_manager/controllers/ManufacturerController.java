package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;
    private static final Logger LOGGER = LogManager.getLogger(ManufacturerController.class);
    private String errorText = "";

    @GetMapping("/manufacturers")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("User go on ManufacturerController page");
        List<Manufacturer> manufacturers;
        if (name != null){
            manufacturers = manufacturerService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        }
        else manufacturers = manufacturerService.getAll();
        model.addAttribute("manufacturerList", manufacturers);
        model.addAttribute("manufacturersSize", manufacturers.size());
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "Manufacturer/manufacturers";
    }

    @RequestMapping("/manufacturers/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id){
        if (manufacturerService.findById(id).isPresent()){
            if (manufacturerService.delete(id)){
                LOGGER.info("Manufacturer with id={} was deleted", id);
            }
            else {
                LOGGER.info("Manufacturer {} with id={} wasn't deleted",
                        manufacturerService.findById(id).get().getName(), id);
                errorText = "Can't delete this model status";
            }
            LOGGER.info("Manufacturer {} with id={} was deleted",
                    manufacturerService.findById(id).get().getName(), id);
        } else LOGGER.warn("Manufacturer with id={} don't exist", id);
        return "redirect:/manufacturers";
    }

    @PostMapping("/manufacturers/add")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer){
        if (!manufacturer.getName().isEmpty()){
            manufacturerService.save(manufacturer);
            LOGGER.info("Manufacturer {} with id={} was added", manufacturer.getName(), manufacturer.getId());
        }
        else{
            LOGGER.warn("Manufacturer wasn't added, name is empty");
            errorText = "Wrong input data";
        }
        return "redirect:/manufacturers";
    }

    @GetMapping("/manufacturers/{id}")
    public String manufacturerEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        LOGGER.info("User want edit Manufacturer with id={}", id);
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
    public String manufacturerUpdate(@PathVariable(value = "id") Long id, @RequestParam String name) {
        LOGGER.info("User want edit Manufacturer with id={}", id);

        if (manufacturerService.findById(id).isEmpty())
            LOGGER.warn("Manufacturer with id={} don't exist", id);
        else if (Objects.equals(name, ""))
            LOGGER.warn("Manufacturer name is empty");
        else {
            Manufacturer manufacturer = manufacturerService.findById(id).get();
            manufacturer.setName(name);
            manufacturerService.save(manufacturer);
            LOGGER.info("Manufacturer {} with id={} was edited successfully", name, id);
            return "redirect:/manufacturers";
        }
        errorText = "Wrong input data";
        return "redirect:/manufacturers";
    }
}

