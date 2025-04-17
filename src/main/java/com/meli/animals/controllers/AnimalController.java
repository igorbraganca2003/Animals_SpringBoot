package com.meli.animals.controllers;

import org.springframework.stereotype.Controller;
import com.meli.animals.entities.Animal;
import com.meli.animals.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @GetMapping("/animais")
    public ResponseEntity<List<Animal>> encontrarTodos() {
        List<Animal> animais = service.encontrarTodosAnimais();
        if (animais.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/animais/id/{id}")
    public ResponseEntity<Animal> findById(@PathVariable Long id) {
        Optional<Animal> animal = service.findById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/animais/cor/{cor}")
    public ResponseEntity<List<Animal>> findByCor(@PathVariable String cor){
        List<Animal> animais = service.findByCor(cor);
        if (animais.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/animais/raca/{raca}")
    public ResponseEntity<List<Animal>> findByRaca (@PathVariable String raca){
        List<Animal> animais = service.findByRaca(raca);
        if (animais.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animais);
    }

    @PostMapping("/animais")
    public ResponseEntity<Animal> salvarAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(201).body(service.save(animal));
    }

    @DeleteMapping("/animais/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        service.deletarAnimal(id);
        return ResponseEntity.noContent().build();
    }

    //    @GetMapping("/animais/raca/{raca}")
//    public ResponseEntity<List<Animal>> findByRaca (@PathVariable String raca){
//        return ResponseEntity.ok(service.findByRaca(raca));
//    }

}
