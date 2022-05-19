package org.netcracker.labs.My_models_manager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "status")
public class Status {
    @Id
    private Long id;

    @Column
    private String name;
}

