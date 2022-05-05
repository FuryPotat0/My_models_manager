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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ModelController implements ControllerInterface<org.netcracker.labs.My_models_manager.entities.Model> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);
    @Autowired
    private ModelService modelService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private ModelStatusService modelStatusService;

    @GetMapping("/models")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on ModelController page");
        List<org.netcracker.labs.My_models_manager.entities.Model> models;
        List<Storage> storages;
        List<Manufacturer> manufacturers;
        List<ModelStatus> modelStatuses;

        if (name != null) {
            models = modelService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else models = modelService.getAll();

        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        storages = storageService.getAll();
        manufacturers = manufacturerService.getAll();
        modelStatuses = modelStatusService.getAll();

        model.addAttribute("modelNumber", models.size());
        model.addAttribute("models", models);
        model.addAttribute("storages", storages);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("modelStatuses", modelStatuses);
        return "Model/models";
    }

    @RequestMapping("/models/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (modelService.findById(id).isPresent()) {
            try {
                modelService.delete(id);
                LOGGER.info("Model with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("Model {} with id={} wasn't deleted",
                        modelService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this model, remove all links to this model to delete it");
            }
        } else LOGGER.warn("Model with id={} don't exist", id);
        return new ModelAndView("redirect:/models", model);
    }

    @PostMapping("/models/add")
    public ModelAndView addEntity(@ModelAttribute org.netcracker.labs.My_models_manager.entities.Model entity,
            ModelMap model) {
        if (entity.getModelStatus() != null && entity.getManufacturer() != null && entity.getStorage() != null)
            if (entity.getName() != null) {
                modelService.save(entity);
                LOGGER.info("Model \"{}\" with id={} was added", entity.getName(), entity.getId());
                return new ModelAndView("redirect:/models", model);
            } else LOGGER.warn("Model wasn't added, name is empty");
        else LOGGER.warn("Model wasn't added, one or more links are null");
        model.addAttribute("errorText","Wrong input data");
        return new ModelAndView("redirect:/models", model);
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
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id,
                               @ModelAttribute org.netcracker.labs.My_models_manager.entities.Model entity,
                                     ModelMap model) {
        LOGGER.info("User want edit Model with id={}", id);

        if (modelService.findById(id).isEmpty())
            LOGGER.warn("Model with id={} don't exist", id);
        else if (!entity.getName().isEmpty()) {
            modelService.save(entity);
            LOGGER.info("Model \"{}\" with id={} was edited successfully", entity.getName(), id);
        } else{
            LOGGER.warn("Model wasn't edited");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/models", model);
    }
}

