package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ModelStatusController {
    @Autowired
    private ModelStatusService modelStatusService;
    private static final Logger LOGGER = LogManager.getLogger(ModelStatusController.class);
    private String errorText = "";

    @GetMapping("/modelStatuses")
    public String getAll(@RequestParam(value = "name", required = false) String name, Model model){
        LOGGER.info("User go on ModelStatusController page");
        List<ModelStatus> modelStatuses;
        if (name != null){
            modelStatuses = modelStatusService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        }
        else modelStatuses = modelStatusService.getAll();
        model.addAttribute("modelStatusList", modelStatuses);
        model.addAttribute("modelStatusSize", modelStatuses.size());
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "ModelStatus/model-statuses";
    }

    @RequestMapping("/modelStatuses/delete/{id}")
    public String deleteModelStatus(@PathVariable Long id){
        if (modelStatusService.findById(id).isPresent()){
            try {
                modelStatusService.delete(id);
                LOGGER.info("ModelStatus with id={} was deleted", id);
            }
            catch (DataIntegrityViolationException e) {
                LOGGER.info("ModelStatus {} with id={} wasn't deleted",
                        modelStatusService.findById(id).get().getName(), id);
                errorText = "Can't delete this ModelStatus, remove all links to this ModelStatus to delete it";
            }
        } else LOGGER.warn("ModelStatus with id={} don't exist", id);
        return "redirect:/modelStatuses";
    }

    @PostMapping("/modelStatuses/add")
    public String addModelStatus(@ModelAttribute ModelStatus modelStatus){
        if (!modelStatus.getName().isEmpty()){
            modelStatusService.save(modelStatus);
            LOGGER.info("ModelStatus {} with id={} was added", modelStatus.getName(), modelStatus.getId());
        }
        else{
            LOGGER.warn("ModelStatus wasn't added, name is empty");
            errorText = "Wrong input data";
        }
        return "redirect:/modelStatuses";
    }

    @GetMapping("/modelStatuses/{id}")
    public String modelStatusEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<ModelStatus> modelStatus = modelStatusService.findById(id);
        LOGGER.info("User want visit ModelStatus with id={}", id);
        if (modelStatus.isPresent()) {
            ArrayList<ModelStatus> res = new ArrayList<>();
            res.add(modelStatus.get());
            model.addAttribute("modelStatus", res);
            LOGGER.info("ModelStatus {} with id={} will be edited", modelStatus.get().getName(), id);
            return "ModelStatus/model-status-edit";
        } else {
            LOGGER.warn("No ModelStatus with id={}", id);
            return "redirect:/modelStatuses";
        }
    }

    @PostMapping("/modelStatuses/{id}")
    public String modelStatusUpdate(@PathVariable(value = "id") Long id, @RequestParam String name) {
        LOGGER.info("User want edit ModelStatus with id={}", id);
        if (modelStatusService.findById(id).isEmpty())
            LOGGER.warn("ModelStatus with id={} don't exist", id);
        else if (Objects.equals(name, ""))
            LOGGER.warn("ModelStatus name is empty");
        else {
            ModelStatus modelStatus = modelStatusService.findById(id).get();
            modelStatus.setName(name);
            modelStatusService.save(modelStatus);
            LOGGER.info("ModelStatus {} with id={} was edited successfully", name, id);
            return "redirect:/modelStatuses";
        }
        errorText = "Wrong input data";
        return "redirect:/modelStatuses";
    }
}

