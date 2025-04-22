package com.meli.animals.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private int idade;

    @Column
    private String cor;

    @Column
    private String raca;

    @OneToOne
    @JoinColumn(name = "tipo_animal_id", referencedColumnName = "id")
    private TipoAnimal tipoAnimal;

    @OneToOne
    @JoinColumn(name = "habitat_id", referencedColumnName = "id")
    private Habitat habitat;

    public void setTipoAnimal(TipoAnimal tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }
}