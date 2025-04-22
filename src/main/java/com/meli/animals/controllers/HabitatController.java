package com.meli.animals.controllers;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import com.meli.animals.entities.Habitat;
import com.meli.animals.services.HabitatServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.meli.animals.Exceptions.HabitatNomeDuplicadoException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatServices service;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AnimalController.class);

    @GetMapping("/habitat")
    public ResponseEntity<List<Habitat>> encontrarTodos() {
        List<Habitat> habitats = service.encontrarTodosHabitats();
        if (habitats.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitats);
    }

    @GetMapping("/habitat/{id}")
    public ResponseEntity<Habitat> encontrar(@PathVariable Long id) {
        Optional<Habitat> habitat = service.encontrarPorId(id);
        return habitat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/habitat/nomeHabitat/{nomeHabitat}")
    public ResponseEntity<Optional<Habitat>> encontrarPorNomeHabitat(@PathVariable String nomeHabitat) {
        Optional<Habitat> habitats = service.findByNomeHabitat(nomeHabitat);
        if (habitats.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitats);
    }

    @PostMapping("/habitat")
    public ResponseEntity<Habitat> salvarHabitat(@RequestBody Habitat habitat) {
        logger.info("Tentando salvar o habitat: {}", habitat);

        String nomeHabitat = habitat.getNomeHabitat();
        if (nomeHabitat != null && !nomeHabitat.isEmpty()) {
            Optional<Habitat> habitatExistente = service.findByNomeHabitat(nomeHabitat);
            if (habitatExistente.isPresent()) {
                logger.warn("Habitat com o nome '{}' já existe.", nomeHabitat);
                throw new HabitatNomeDuplicadoException(nomeHabitat);
            }
        }

        Habitat habitatSalvo = service.save(habitat);
        logger.info("Habitat salvo com sucesso: {}", habitatSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitatSalvo);
    }

    @PutMapping("/habitat/{id}")
    public ResponseEntity<Habitat> update(@PathVariable Long id, @RequestBody Habitat habitatAtualizado) {
        Optional<Habitat> habitatExistente = service.encontrarPorId(id);

        if (!habitatExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Habitat habitat = habitatExistente.get();

        habitat.setNomeHabitat(habitatAtualizado.getNomeHabitat());

        Habitat habitatlSalvo = service.save(habitat);
        return ResponseEntity.ok(habitatlSalvo);
    }

    @DeleteMapping("/habitat/{id}")
    public ResponseEntity<Void> deletarHabitat(@PathVariable Long id) {
        service.deletarHabitat(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(HabitatNomeDuplicadoException.class)
    public ResponseEntity<String> handleHabitatNomeDuplicadoException(HabitatNomeDuplicadoException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
