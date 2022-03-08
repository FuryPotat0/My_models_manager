package org.netcracker.labs.My_models_manager.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer manufacturer_id;

    @Getter
    @Setter
    @Column(unique = true)
    private String manufacturer_name;

    public Manufacturer(){

    }
}

