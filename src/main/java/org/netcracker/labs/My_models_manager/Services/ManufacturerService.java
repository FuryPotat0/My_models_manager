package org.netcracker.labs.My_models_manager.Services;

import org.netcracker.labs.My_models_manager.Entities.Manufacturer;
import org.netcracker.labs.My_models_manager.Repositories.ManufacturersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturersRepository manufacturersRepository;

    public List<Manufacturer> getAll(){
        return (List<Manufacturer>) manufacturersRepository.findAll();
    }

    public Manufacturer save(Manufacturer manufacturer){
        return manufacturersRepository.save(manufacturer);
    }

    public void delete(int id){
        manufacturersRepository.deleteById(id);
    }
}

