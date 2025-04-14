package com.meli.animals.services;

import com.meli.animals.entities.TipoAnimal;

import java.util.List;
import java.util.Optional;

public interface TipoAnimalService {

    TipoAnimal save(TipoAnimal tipoAnimal);
    void delete(Long id);

}
