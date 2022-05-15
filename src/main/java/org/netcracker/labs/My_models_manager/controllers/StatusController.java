package org.netcracker.labs.My_models_manager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.entities.Status;
import org.netcracker.labs.My_models_manager.services.StatusService;
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
public class StatusController implements ControllerInterface<Status> {
    private static final Logger LOGGER = LogManager.getLogger(StatusController.class);
    @Autowired
    private StatusService statusService;

    @GetMapping("/statuses")
    public String getAll(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "errorText", required = false) String errorText, Model model) {
        LOGGER.info("User go on StatusController page");
        List<Status> statuses;
        if (name != null) {
            statuses = statusService.findAllByName(name);
            LOGGER.info("search answers by name={}", name);
        } else statuses = statusService.getAll();
        if (errorText != null) {
            model.addAttribute("errorText", errorText);
        }
        model.addAttribute("highlighted", new FormCheckboxes());
        model.addAttribute("statusList", statuses);
        model.addAttribute("statusSize", statuses.size());
        return "Status/statuses";
    }

    @RequestMapping("/statuses/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable Long id, ModelMap model) {
        if (statusService.findById(id).isPresent()) {
            try {
                statusService.delete(id);
                LOGGER.info("Status with id={} was deleted", id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.info("Status {} with id={} wasn't deleted",
                        statusService.findById(id).get().getName(), id);
                model.addAttribute("errorText",
                        "Can't delete this Status, remove all links to this Status to delete it");
            }
        } else LOGGER.warn("Status with id={} don't exist", id);
        return new ModelAndView("redirect:/statuses", model);
    }

    @PostMapping("/statuses/add")
    public ModelAndView addEntity(@ModelAttribute Status status, ModelMap model) {
        if (!status.getName().isEmpty()) {
            statusService.save(status);
            LOGGER.info("Status {} with id={} was added", status.getName(), status.getId());
        } else {
            LOGGER.warn("Status wasn't added, name is empty");
            model.addAttribute("errorText", "Wrong input data");
        }
        return new ModelAndView("redirect:/statuses", model);
    }

    @GetMapping("/statuses/{id}")
    public String editEntity(@PathVariable(value = "id") Long id, Model model) {
        Optional<Status> modelStatus = statusService.findById(id);
        LOGGER.info("User want visit ModelStatus with id={}", id);
        if (modelStatus.isPresent()) {
            ArrayList<Status> res = new ArrayList<>();
            res.add(modelStatus.get());
            model.addAttribute("status", res);
            LOGGER.info("ModelStatus {} with id={} will be edited", modelStatus.get().getName(), id);
            return "Status/status-edit";
        } else {
            LOGGER.warn("No Status with id={}", id);
            return "redirect:/statuses";
        }
    }

    @PostMapping("/statuses/{id}")
    public ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute Status status,
                                     ModelMap model) {
        LOGGER.info("User want edit Status with id={}", id);
        if (statusService.findById(id).isEmpty())
            LOGGER.warn("Status with id={} don't exist", id);
        else if (!status.getName().isEmpty()) {
            statusService.update(status);
            LOGGER.info("Status {} with id={} was edited successfully", status.getName(), id);
        } else {
            model.addAttribute("errorText","Wrong input data");
        }
        return new ModelAndView("redirect:/statuses", model);
    }

    @PostMapping("/statuses/deleteHighlighted")
    public ModelAndView deleteHighlighted(FormCheckboxes ids, ModelMap model) {
        try {
            statusService.deleteHighlighted(ids);
            LOGGER.info("Highlighted Statuses were deleted");
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Highlighted Statuses weren't deleted");
            model.addAttribute("errorText",
                    "Can't delete highlighted statuses," +
                            " remove all links to statuses to delete them");
        }
        return new ModelAndView("redirect:/statuses", model);
    }
}

