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
public class Place {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Room room;
}

