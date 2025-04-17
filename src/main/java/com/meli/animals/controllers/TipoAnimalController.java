package com.meli.animals.controllers;

import org.springframework.stereotype.Controller;
import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.services.TipoAnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TipoAnimalController {

    private final TipoAnimalService service;

    @GetMapping("/TipoAnimal")
    public ResponseEntity<List<TipoAnimal>> encontrarTodos() {
        List<TipoAnimal> tipos = service.encontrarTodosTipos();
        if (tipos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/TipoAnimal/id/{id}")
    public ResponseEntity<TipoAnimal> encontrarPorId(@PathVariable Long id) {
        Optional<TipoAnimal> tipo = service.encontrarPorId(id);
        return tipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/TipoAnimal")
    public ResponseEntity<TipoAnimal> save(@RequestBody TipoAnimal tipoAnimal) {
        return ResponseEntity.status(201).body(service.save(tipoAnimal));
    }

    @PutMapping("/TipoAnimal/{id}")
    public ResponseEntity<TipoAnimal> update(@PathVariable Long id, @RequestBody TipoAnimal tipoAnimalAtualizado) {
        Optional<TipoAnimal> tipoAnimalExistente = service.encontrarPorId(id);

        if (!tipoAnimalExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TipoAnimal tipoAnimal = tipoAnimalExistente.get();

        tipoAnimal.setDescricao(tipoAnimalAtualizado.getDescricao());
        tipoAnimal.setRaca(tipoAnimalAtualizado.getRaca());

        TipoAnimal tipoAnimalSalvo = service.save(tipoAnimal);
        return ResponseEntity.ok(tipoAnimalSalvo);
    }

    @DeleteMapping("/TipoAnimal/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
