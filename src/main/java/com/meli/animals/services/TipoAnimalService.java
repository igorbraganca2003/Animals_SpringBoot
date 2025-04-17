package com.meli.animals.services;

import com.meli.animals.entities.TipoAnimal;

import java.util.List;
import java.util.Optional;

public interface TipoAnimalService {

    TipoAnimal save(TipoAnimal tipoAnimal);

    List<TipoAnimal> encontrarTodosTipos();

    Optional<TipoAnimal> encontrarPorId(Long id);

    void delete(Long id);

}
