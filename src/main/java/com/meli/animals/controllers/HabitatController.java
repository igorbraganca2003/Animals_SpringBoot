package com.meli.animals.controllers;

import org.springframework.stereotype.Controller;
import com.meli.animals.entities.Habitat;
import com.meli.animals.services.HabitatServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatServices service;

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
    public ResponseEntity<List<Habitat>> encontrarPorNomeHabitat(@PathVariable String nomeHabitat) {
        List<Habitat> habitats = service.findByNomeHabitat(nomeHabitat);
        if (habitats.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitats);
    }

    @PostMapping("/habitat")
    public ResponseEntity<Habitat> salvarHabitat(@RequestBody Habitat habitat) {
        return ResponseEntity.status(201).body(service.save(habitat));
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
}
