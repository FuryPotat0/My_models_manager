package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.netcracker.labs.My_models_manager.services.ManufacturerService;
import org.netcracker.labs.My_models_manager.services.ModelService;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
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
public class ModelController {
    @Autowired
    private ModelService modelService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ModelStatusService modelStatusService;

    private String errorText = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);

    @GetMapping("/models")
    public String models(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("User go on ModelController page");
        List<org.netcracker.labs.My_models_manager.entities.Model> models;
        List<Storage> storages;
        List<Manufacturer> manufacturers;
        List<ModelStatus> modelStatuses;
        if (name != null){
            models = modelService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else models = modelService.getAll();
        storages = storageService.getAll();
        manufacturers = manufacturerService.getAll();
        modelStatuses = modelStatusService.getAll();
        model.addAttribute("modelNumber", models.size());
        model.addAttribute("models", models);
        model.addAttribute("storages", storages);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("modelStatuses", modelStatuses);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "Model/models";
    }

    @RequestMapping("/models/delete/{id}")
    public String deleteModel(@PathVariable Long id) {
        if (modelService.findById(id).isPresent()) {
            modelService.delete(id);
            LOGGER.info("Model \"{}\" with id={} was deleted", modelService.findById(id).get().getName(), id);
        } else {
            LOGGER.warn("Model with id={} don't exist", id);
        }
        return "redirect:/models";
    }

    @PostMapping("/models/add")
    public String addModel(@ModelAttribute org.netcracker.labs.My_models_manager.entities.Model model) {
        if (model.getModelStatus() != null && model.getManufacturer() != null && model.getStorage() != null)
            if (model.getName() != null) {
                modelService.save(model);
                LOGGER.info("Model \"{}\" with id={} was added", model.getName(), model.getId());
                return "redirect:/models";
            } else LOGGER.warn("Model wasn't added, name is empty");
        else LOGGER.warn("Model wasn't added, one or more links are null");
        errorText = "Wrong input data";
        return "redirect:/models";
    }

    @GetMapping("/models/{id}")
    public String modelEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<org.netcracker.labs.My_models_manager.entities.Model> modelOptional = modelService.findById(id);
        LOGGER.info("User want edit Model with id={}", id);
        if (modelOptional.isPresent()) {
            ArrayList<org.netcracker.labs.My_models_manager.entities.Model> res = new ArrayList<>();
            res.add(modelOptional.get());
            List<Storage> storages = storageService.getAll();
            List<Manufacturer> manufacturers = manufacturerService.getAll();
            List<ModelStatus> modelStatuses = modelStatusService.getAll();
            model.addAttribute("models", res);
            model.addAttribute("storages", storages);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("modelStatuses", modelStatuses);
            LOGGER.info("Model \"{}\" with id={} will be edited", modelOptional.get().getName(), id);
            return "Model/model-edit";
        } else {
            LOGGER.warn("No Model with id={}", id);
            return "redirect:/models";
        }
    }

    @PostMapping("/models/{id}")
    public String modelUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                              @RequestParam String description, @RequestParam String manufacturer,
                              @RequestParam String modelStatus, @RequestParam String storage) {
        LOGGER.info("User want edit Model with id={}", id);
        Long manufacturerId = Long.parseLong(manufacturer);
        Long modelStatusId = Long.parseLong(modelStatus);
        Long storageId = Long.parseLong(storage);

        if (modelService.findById(id).isEmpty())
            LOGGER.warn("Model with id={} don't exist", id);
        else if (manufacturerService.findById(manufacturerId).isEmpty() ||
                modelStatusService.findById(modelStatusId).isEmpty() || storageService.findById(storageId).isEmpty())
            LOGGER.warn("Links with Model don't exist");
        else if (Objects.equals(name, ""))
            LOGGER.warn("Model name is empty");
        else {
            org.netcracker.labs.My_models_manager.entities.Model model = modelService.findById(id).get();
            model.setName(name);
            model.setDescription(description);
            model.setModelStatus(modelStatusService.findById(modelStatusId).get());
            model.setManufacturer(manufacturerService.findById(manufacturerId).get());
            model.setStorage(storageService.findById(storageId).get());
            modelService.save(model);
            LOGGER.info("Model \"{}\" with id={} was edited successfully", name, id);
            return "redirect:/models";
        }
        errorText = "Wrong input data";
        return "redirect:/models";
    }
}

