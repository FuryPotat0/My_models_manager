package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.netcracker.labs.My_models_manager.repositories.ModelStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelStatusService {
    @Autowired
    ModelStatusRepository modelStatusRepository;

    public List<ModelStatus> getAll(){
        return (List<ModelStatus>) modelStatusRepository.findAll();
    }

    public ModelStatus save(ModelStatus modelStatus){
        return modelStatusRepository.save(modelStatus);
    }

    public void delete(Long id){
        modelStatusRepository.deleteById(id);
    }

    public List<ModelStatus> findAllByName(String name){
        List<ModelStatus> modelStatuses = new ArrayList<>();
        for (ModelStatus modelStatus: modelStatusRepository.findAll()){
            if(modelStatus.getName().toLowerCase().contains(name.toLowerCase())){
                modelStatuses.add(modelStatus);
            }
        }
        return modelStatuses;
    }
}

