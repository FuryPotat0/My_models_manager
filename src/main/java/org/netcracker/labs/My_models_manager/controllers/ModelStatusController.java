package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModelStatusController {
    @Autowired
    private ModelStatusService modelStatusService;

    private String errorText = "";

    @GetMapping("/modelStatus")
    public String getAll(Model model){
        List<ModelStatus> modelStatuses = modelStatusService.getAll();
        model.addAttribute("modelStatusList", modelStatuses);
        model.addAttribute("errorText", errorText);
        errorText = "";
        return "model_statuses";
    }
}

