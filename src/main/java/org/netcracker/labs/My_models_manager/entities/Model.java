package org.netcracker.labs.My_models_manager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Model {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private int modelsInSquad;

    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Manufacturer manufacturer;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private ModelStatus modelStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Storage storage;
}
