package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface ControllerInterface<T> {
    String getAll(@RequestParam(value = "name", required = false) String name,
                  @RequestParam(value = "errorText", required = false) String errorText, Model model);

    ModelAndView deleteEntity(@PathVariable Long id, ModelMap model);

    ModelAndView addEntity(@ModelAttribute T entity, ModelMap model);

    String editEntity(@PathVariable(value = "id") Long id, Model model);

    ModelAndView updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute T entity, ModelMap model);

    ModelAndView deleteHighlighted(@ModelAttribute("highlighted") FormCheckboxes ids, ModelMap model);
}
