package com.meli.animals.services;

import com.meli.animals.entities.Habitat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HabitatServices {

    Habitat save(Habitat habitat);

    List<Habitat> encontrarTodosHabitats();

    Optional<Habitat> encontrarPorId(Long id);

    void deletarHabitat(Long id);
}
