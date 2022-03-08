package org.netcracker.labs.My_models_manager.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Getter
    @Setter
    private String name;

    public Room() {
    }
}

