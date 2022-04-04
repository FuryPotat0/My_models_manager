package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.entities.Squad;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.services.ManufacturerService;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
import org.netcracker.labs.My_models_manager.services.SquadService;
import org.netcracker.labs.My_models_manager.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SquadController {
    @Autowired
    private SquadService squadService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ModelStatusService modelStatusService;

    private String errorText = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(SquadController.class);

    @GetMapping("/squads")
    public String squads(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("User go on SquadController page");
        List<Squad> squads;
        List<Storage> storages;
        List<Manufacturer> manufacturers;
        List<ModelStatus> modelStatuses;
        if (name != null){
            squads = squadService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else squads = squadService.getAll();
        storages = storageService.getAll();
        manufacturers = manufacturerService.getAll();
        modelStatuses = modelStatusService.getAll();
        model.addAttribute("squadNumber", squads.size());
        model.addAttribute("squads", squads);
        model.addAttribute("storages", storages);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("modelStatuses", modelStatuses);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "Squad/squads";
    }

    @RequestMapping("/squads/delete/{id}")
    public String deleteSquad(@PathVariable Long id) {
        if (squadService.findById(id).isPresent()) {
            squadService.delete(id);
            LOGGER.info("Squad \"{}\" with id={} was deleted", squadService.findById(id).get().getName(), id);
        } else {
            LOGGER.warn("Squad with id={} don't exist", id);
        }
        return "redirect:/squads";
    }

    @PostMapping("/squads/add")
    public String addSquad(@ModelAttribute Squad model) {
        if (model.getModelStatus() != null && model.getManufacturer() != null && model.getStorage() != null)
            if (model.getName() != null) {
                squadService.save(model);
                LOGGER.info("Squad \"{}\" with id={} was added", model.getName(), model.getId());
                return "redirect:/squads";
            } else LOGGER.warn("Squad wasn't added, name is empty");
        else LOGGER.warn("Squad wasn't added, one or more links are null");
        errorText = "Wrong input data";
        return "redirect:/squads";
    }

    @GetMapping("/squads/{id}")
    public String squadEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Squad> squad = squadService.findById(id);
        LOGGER.info("User want edit Squad with id={}", id);
        if (squad.isPresent()) {
            ArrayList<Squad> res = new ArrayList<>();
            res.add(squad.get());
            List<Storage> storages = storageService.getAll();
            List<Manufacturer> manufacturers = manufacturerService.getAll();
            List<ModelStatus> modelStatuses = modelStatusService.getAll();
            model.addAttribute("squads", res);
            model.addAttribute("storages", storages);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("modelStatuses", modelStatuses);
            LOGGER.info("Squad \"{}\" with id={} will be edited", squad.get().getName(), id);
            return "Squad/squad-edit";
        } else {
            LOGGER.warn("No Squad with id={}", id);
            return "redirect:/squads";
        }
    }

//    @PostMapping("/models/{id}")
//    public String squadUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
//                              @RequestParam String description, @RequestParam String modelsInSquad,
//                              @RequestParam String manufacturerId,
//                              @RequestParam String modelStatusId, @RequestParam String storageId) {
//        LOGGER.info("User want edit Squad with id={}", id);
//
//
//
//        if (squadService.findById(id).isEmpty())
//            LOGGER.warn("Squad with id={} don't exist", id);
//        else if (manufacturerService.findById(manufacturerId).isEmpty() ||
//                modelStatusService.findById(modelStatusId).isEmpty() || storageService.findById(storageId).isEmpty())
//            LOGGER.warn("Links with Model don't exist");
//        else if (Objects.equals(name, ""))
//            LOGGER.warn("Squad name is empty");
//        else {
//            org.netcracker.labs.My_models_manager.entities.Model model = modelService.findById(id).get();
//            model.setName(name);
//            model.setDescription(description);
//            model.setModelStatus(modelStatusService.findById(modelStatusId).get());
//            model.setManufacturer(manufacturerService.findById(manufacturerId).get());
//            model.setStorage(storageService.findById(storageId).get());
//            modelService.save(model);
//            LOGGER.info("Model \"{}\" with id={} was edited successfully", name, id);
//            return "redirect:/models";
//        }
//        errorText = "Wrong input data";
//        return "redirect:/models";
//    }
}

