package com.meli.animals.controllers;

import com.meli.animals.entities.Animal;
import com.meli.animals.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    // controller -> service -> repository -> banco de dados <- repository <- service <- controller

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

}

/**
 * Etapas:
 *  criar model na pasta model para representar o modelo salvo no banco de dados
 *  criar repository extendendo JpaRepository para comunicar com o banco
 *  criar service (camada logica) para chamar a repository e salvar o animal
 *  criar controller para criar as rotas e chamar as services
 */


//Desafio parte 1
/** ✅
 * Desafio: está faltando uma annotation (@) dentro dos parâmetros do salvarAnimal,
 * para representar que o Animal vai vir no payload (json)
 * Pesquisar qual annotation representa o ***Request Body*** no spring
 * */


//Desafio parte 2:
/** ✅
 * terminal de implementar encontrarAnimalPorId (utilizar findById() do jpa repository)
 * fazer o deletarAnimal
 * */

//Desafio parte 3:
/** ✅
 *  - fazer encontrar Lista de animais por cor
 *  - fazer encontrar lista de animais por raça (colocar raça na model)
 * */
