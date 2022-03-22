package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;
    private static final Logger LOGGER = LogManager.getLogger("ManufacturersLogger");

    @GetMapping("/manufacturers")
    public String getAll(Model model){
        List<Manufacturer> manufacturerList = manufacturerService.getAll();
        model.addAttribute("manufacturerList", manufacturerList);
        model.addAttribute("manufacturersSize", manufacturerList.size());
        return "manufacturers";
    }

    @RequestMapping("/manufacturers/delete/{id}")
    public String deleteManufacturer(@PathVariable int id){
        manufacturerService.delete(id);
        return "redirect:/manufacturers";
    }

    @PostMapping("/manufacturers")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer){
        try {
            if(!Objects.equals(manufacturer.getName(), ""))
                manufacturerService.save(manufacturer);
            else throw new IllegalArgumentException("");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return "redirect:/manufacturers";
    }
}

