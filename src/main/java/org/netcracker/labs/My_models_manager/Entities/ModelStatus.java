package org.netcracker.labs.My_models_manager.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ModelStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true)
    private String name;

    public ModelStatus() {
    }
}

