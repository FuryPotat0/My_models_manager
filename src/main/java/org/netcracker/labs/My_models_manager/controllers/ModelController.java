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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ModelController implements ControllerInterface<org.netcracker.labs.My_models_manager.entities.Model> {
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
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model){
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
    public String deleteEntity(@PathVariable Long id) {
        if (modelService.findById(id).isPresent()) {
            try {
                modelService.delete(id);
                LOGGER.info("Model with id={} was deleted", id);
            }
            catch (DataIntegrityViolationException e) {
                LOGGER.info("Model {} with id={} wasn't deleted",
                        modelService.findById(id).get().getName(), id);
                errorText = "Can't delete this model, remove all links to this model to delete it";
            }
        } else LOGGER.warn("Model with id={} don't exist", id);
        return "redirect:/models";
    }

    @PostMapping("/models/add")
    public String addEntity(@ModelAttribute org.netcracker.labs.My_models_manager.entities.Model model) {
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
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
        Optional<org.netcracker.labs.My_models_manager.entities.Model> modelOptional = modelService.findById(id);
        LOGGER.info("User want visit Model with id={}", id);
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
    public String updateEntity(@PathVariable(value = "id") Long id,
                               @ModelAttribute org.netcracker.labs.My_models_manager.entities.Model model) {
        LOGGER.info("User want edit Model with id={}", id);

        if (modelService.findById(id).isEmpty())
            LOGGER.warn("Model with id={} don't exist", id);
        else
            if (!model.getName().isEmpty()){
                modelService.save(model);
                LOGGER.info("Model \"{}\" with id={} was edited successfully", model.getName(), id);
            }
            else errorText = "Wrong input data";
        return "redirect:/models";
    }
}

