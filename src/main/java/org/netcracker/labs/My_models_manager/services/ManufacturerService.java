package org.netcracker.labs.My_models_manager.services;

import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAll(){
        return (List<Manufacturer>) manufacturerRepository.findAll();
    }

    public void save(Manufacturer manufacturer){
        manufacturerRepository.save(manufacturer);
    }

    public void delete(int id){
        manufacturerRepository.deleteById(id);
    }

    public List<Manufacturer> findAllByName(String name){
        List<Manufacturer> manufacturers = new ArrayList<>();
        for (Manufacturer manufacturer: manufacturerRepository.findAll()){
            if(manufacturer.getName().toLowerCase().contains(name.toLowerCase())){
                manufacturers.add(manufacturer);
            }
        }
        return manufacturers;
    }
}

