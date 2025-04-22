package com.meli.animals.controllers;

import com.meli.animals.entities.Animal;
import com.meli.animals.entities.Habitat;
import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.services.AnimalService;
import com.meli.animals.repositories.TipoAnimalRepository;
import com.meli.animals.repositories.HabitatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;
    private final TipoAnimalRepository tipoAnimalRepository;
    private final HabitatRepository habitatRepository;

    @GetMapping
    public ResponseEntity<List<Animal>> encontrarTodos() {
        List<Animal> animais = service.encontrarTodosAnimais();
        if (animais.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Animal> findById(@PathVariable Long id) {
        Optional<Animal> animal = service.findById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cor/{cor}")
    public ResponseEntity<List<Animal>> findByCor(@PathVariable String cor) {
        List<Animal> animais = service.findByCor(cor);
        if (animais.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/raca/{raca}")
    public ResponseEntity<List<Animal>> findByRaca(@PathVariable String raca) {
        List<Animal> animais = service.findByRaca(raca);
        if (animais.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @PostMapping
    public ResponseEntity<Animal> salvarAnimal(@RequestBody Animal animal) {
        if (animal.getTipoAnimal() != null) {
            Long tipoAnimalId = animal.getTipoAnimal().getId();
            Optional<TipoAnimal> tipoAnimalExistente = tipoAnimalRepository.findById(tipoAnimalId);
            tipoAnimalExistente.ifPresentOrElse(animal::setTipoAnimal, () -> {
                throw new EntityNotFoundException("TipoAnimal with ID " + tipoAnimalId + " not found.");
            });
        }

        if (animal.getHabitat() != null) {
            Long habitatId = animal.getHabitat().getId();
            Optional<Habitat> habitatExistente = habitatRepository.findById(habitatId);
            habitatExistente.ifPresentOrElse(animal::setHabitat, () -> {
                throw new EntityNotFoundException("Habitat with ID " + habitatId + " not found.");
            });
        }

        Animal animalSalvo = service.save(animal);
        return ResponseEntity.status(201).body(animalSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        service.deletarAnimal(id);
        return ResponseEntity.noContent().build();
    }
}