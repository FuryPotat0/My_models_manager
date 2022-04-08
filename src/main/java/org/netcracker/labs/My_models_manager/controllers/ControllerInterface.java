package org.netcracker.labs.My_models_manager.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerInterface<T> {
    String getAll(@RequestParam(value = "name", required = false) String name, Model model);

    String deleteEntity(@PathVariable Long id);

    String addEntity(@ModelAttribute T entity);

    String editEntity(@PathVariable(value = "id") Long id, Model model);

    String updateEntity(@PathVariable(value = "id") Long id, @ModelAttribute T entity);
}
