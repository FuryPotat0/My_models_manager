package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.Entities.Manufacturer;
import org.netcracker.labs.My_models_manager.Services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/")
    public String getAll(Model model){
        List<Manufacturer> manufacturerList = manufacturerService.getAll();
        model.addAttribute("manufacturerList", manufacturerList);
        model.addAttribute("manufacturersSize", manufacturerList.size());
        return "index";
    }

    @RequestMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable int id){
        manufacturerService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer){
        manufacturerService.save(manufacturer);
        return "redirect:/";
    }
}

