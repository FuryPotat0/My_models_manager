package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.services.ManufacturerService;
import org.netcracker.labs.My_models_manager.services.ModelService;
import org.netcracker.labs.My_models_manager.services.ModelStatusService;
import org.netcracker.labs.My_models_manager.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "models";
    }
}

