package com.meli.animals.controllers;

import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.services.TipoAnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TipoAnimalController {

//    private final TipoAnimalService service;

//    @GetMapping("/TipoAnimal")
//    public ResponseEntity<List<TipoAnimal>> findAll() {
//        List<TipoAnimal> tipos = service.findAllAnimals();
//    }
}
