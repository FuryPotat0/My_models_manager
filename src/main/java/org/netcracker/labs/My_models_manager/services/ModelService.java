package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Model;
import org.netcracker.labs.My_models_manager.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public List<Model> getAll(){
        return (List<Model>) modelRepository.findAll();
    }

    public void save(Model model){
        modelRepository.save(model);
    }

    public void delete(Long id){
        modelRepository.deleteById(id);
    }

    public List<Model> findAllByName(String name){
        List<Model> models = new ArrayList<>();
        for (Model model: modelRepository.findAll()){
            if(model.getName().toLowerCase().contains(name.toLowerCase())){
                models.add(model);
                System.out.println(model.getName());
                System.out.println(name);
            }
        }
        return models;
    }
}

