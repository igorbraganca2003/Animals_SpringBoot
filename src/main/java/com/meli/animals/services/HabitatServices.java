package com.meli.animals.services;

import com.meli.animals.entities.Habitat;

import java.util.List;
import java.util.Optional;

public interface HabitatServices {

    Habitat save(Habitat habitat);

    List<Habitat> encontrarTodosHabitats();

    List<Habitat> findByNomeHabitat(String nomeHabitat);

    Optional<Habitat> encontrarPorId(Long id);

    void deletarHabitat(Long id);
}
