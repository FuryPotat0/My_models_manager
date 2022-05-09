package org.netcracker.labs.My_models_manager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturer")
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
}

