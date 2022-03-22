package org.netcracker.labs.My_models_manager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private int modelsInSquad;

    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private Manufacturer manufacturer;

    @OneToOne(fetch = FetchType.LAZY)
    private ModelStatus modelStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Storage storage;
}
