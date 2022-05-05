package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
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
public class ModelStatusController implements ControllerInterface<ModelStatus> {
    private static final Logger LOGGER = LogManager.getLogger(ModelStatusController.class);
    @Autowired
    private ModelStatusService modelStatusService;

    @GetMapping("/modelStatuses")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on ModelStatusController page");
        List<ModelStatus> modelStatuses;
        if (name != null) {
            modelStatuses = modelStatusService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else modelStatuses = modelStatusService.getAll();
        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }

        model.addAttribute("modelStatusList", modelStatuses);
        model.addAttribute("modelStatusSize", modelStatuses.size());
        return "ModelStatus/model-statuses";
    }

    @RequestMapping("/modelStatuses/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (modelStatusService.findById(id).isPresent()) {
            try {
                modelStatusService.delete(id);
                LOGGER.info("ModelStatus with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("ModelStatus {} with id={} wasn't deleted",
                        modelStatusService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this ModelStatus, remove all links to this ModelStatus to delete it");
            }
        } else LOGGER.warn("ModelStatus with id={} don't exist", id);
        return new ModelAndView("redirect:/modelStatuses", model);
    }

    @PostMapping("/modelStatuses/add")
    public ModelAndView addEntity(@ModelAttribute ModelStatus modelStatus, ModelMap model) {
        if (!modelStatus.getName().isEmpty()) {
            modelStatusService.save(modelStatus);
            LOGGER.info("ModelStatus {} with id={} was added", modelStatus.getName(), modelStatus.getId());
        } else {
            LOGGER.warn("ModelStatus wasn't added, name is empty");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/modelStatuses", model);
    }

    @GetMapping("/modelStatuses/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
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
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute ModelStatus modelStatus,
                                     ModelMap model) {
        LOGGER.info("User want edit ModelStatus with id={}", id);
        if (modelStatusService.findById(id).isEmpty())
            LOGGER.warn("ModelStatus with id={} don't exist", id);
        else if (!modelStatus.getName().isEmpty()) {
            modelStatusService.save(modelStatus);
            LOGGER.info("ModelStatus {} with id={} was edited successfully", modelStatus.getName(), id);
        } else {
            model.addAttribute("errorText","Wrong input data");
        }
        return new ModelAndView("redirect:/modelStatuses", model);
    }
}

