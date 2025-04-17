package com.meli.animals.services;

import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.repositories.TipoAnimalRepository;
import com.meli.animals.repositories.TipoAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoAnimalServiceImpl implements TipoAnimalService {

    private final TipoAnimalRepository tipoAnimalRepository;

    @Override
    public TipoAnimal save(TipoAnimal tipoAnimal) {
        return tipoAnimalRepository.save(tipoAnimal);
    }

    @Override
    public List<TipoAnimal> encontrarTodosTipos() {
        return tipoAnimalRepository.findAll();
    }

    @Override
    public Optional<TipoAnimal> encontrarPorId(Long id) {
        return tipoAnimalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        tipoAnimalRepository.deleteById(id);
    }
}
