package com.meli.animals.services;

import com.meli.animals.entities.TipoAnimal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TipoAnimalService {

    TipoAnimal save(TipoAnimal tipoAnimal);

    List<TipoAnimal> encontrarTodosTipos();

    Optional<TipoAnimal> encontrarPorId(Long id);

    void delete(Long id);

}
