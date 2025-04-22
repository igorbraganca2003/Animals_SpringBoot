package com.meli.animals.controllers;

import ch.qos.logback.classic.Logger;
import com.meli.animals.entities.Animal;
import com.meli.animals.entities.Habitat;
import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.repositories.AnimalRepository;
import com.meli.animals.services.AnimalService;
import com.meli.animals.repositories.TipoAnimalRepository;
import com.meli.animals.repositories.HabitatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import com.meli.animals.Exceptions.AnimalNomeDuplicadoException;


import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;
    private final TipoAnimalRepository tipoAnimalRepository;
    private final HabitatRepository habitatRepository;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AnimalController.class);
    private final AnimalRepository animalRepository;


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

//    @PostMapping
//    public ResponseEntity<Animal> salvarAnimal(@RequestBody Animal animal) {
//        logger.info("Tentando salvar o animal: {}", animal);
//
//        String nome = animal.getNome();
//        if (nome != null && !nome.trim().isEmpty()) {
//            Optional<Animal> animalExistente = animalRepository.findByNome(nome);
//            if (animalExistente.isPresent()) {
//                logger.warn("Animal com o nome '{}' já existe.", nome);
//                throw new AnimalNomeDuplicadoException(nome);
//            }
//        }
//
//        Animal animalSalvo = service.save(animal);
//        logger.info("Animal salvo com sucesso: {}", animalSalvo);
//        return ResponseEntity.status(HttpStatus.CREATED).body(animalSalvo);
//    }

    @PostMapping
    public ResponseEntity<Animal> salvarAnimal(@RequestBody Animal animal) {
        logger.info("Tentando salvar o animal: {}", animal);

        String nome = animal.getNome();
        if (nome != null && !nome.trim().isEmpty() && service.existsByNome(nome)) {
            logger.warn("Animal com o nome '{}' já existe.", nome);
            throw new AnimalNomeDuplicadoException(nome);
        }

        Animal animalSalvo = service.save(animal);
        logger.info("Animal salvo com sucesso: {}", animalSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        service.deletarAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AnimalNomeDuplicadoException.class)
    public ResponseEntity<String> handleAnimalNomeDuplicadoException(AnimalNomeDuplicadoException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}