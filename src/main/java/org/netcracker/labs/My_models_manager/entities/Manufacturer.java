package org.netcracker.labs.My_models_manager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer {
    @Id
    private Long id;

    @Column(unique = true)
    private String name;
}

