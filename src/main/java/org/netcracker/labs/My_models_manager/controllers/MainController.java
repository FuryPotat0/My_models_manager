package org.netcracker.labs.My_models_manager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String main(Model model){
        LOGGER.info("user go on {} page", MainController.class);
        return "index";
    }
}

