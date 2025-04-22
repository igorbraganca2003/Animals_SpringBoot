package com.meli.animals.controllers;

import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.services.TipoAnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TipoAnimalControllerTest {

    @Mock
    private TipoAnimalService service;

    @InjectMocks
    private TipoAnimalController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveEncontrarTodosOsTiposAnimais() {
        List<TipoAnimal> tipos = Collections.singletonList(new TipoAnimal());
        when(service.encontrarTodosTipos()).thenReturn(tipos);

        ResponseEntity<List<TipoAnimal>> response = controller.encontrarTodos();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipos, response.getBody());
    }

    @Test
    void deveRetornarNotFoundQuandoNenhumTipoAnimalEncontrado() {
        when(service.encontrarTodosTipos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TipoAnimal>> response = controller.encontrarTodos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveEncontrarTipoAnimalPorId() {
        TipoAnimal tipoAnimal = new TipoAnimal();
        when(service.encontrarPorId(1L)).thenReturn(Optional.of(tipoAnimal));

        ResponseEntity<TipoAnimal> response = controller.encontrarPorId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipoAnimal, response.getBody());
    }

    @Test
    void deveRetornarNotFoundParaTipoAnimalNaoExistentePorId() {
        when(service.encontrarPorId(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<TipoAnimal> response = controller.encontrarPorId(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveEncontrarTipoAnimalPorRaca() {
        List<TipoAnimal> tipos = Collections.singletonList(new TipoAnimal());
        when(service.findByRaca("Labrador")).thenReturn(tipos);

        ResponseEntity<List<TipoAnimal>> response = controller.findByRaca("Labrador");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipos, response.getBody());
    }

    @Test
    void deveRetornarNotFoundParaRacaNaoExistente() {
        when(service.findByRaca(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<TipoAnimal>> response = controller.findByRaca("Inexistente");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveSalvarTipoAnimal() {
        TipoAnimal tipoAnimal = new TipoAnimal();
        when(service.save(any(TipoAnimal.class))).thenReturn(tipoAnimal);

        ResponseEntity<TipoAnimal> response = controller.save(tipoAnimal);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(tipoAnimal, response.getBody());
    }

    @Test
    void deveAtualizarTipoAnimal() {
        TipoAnimal tipoAnimal = new TipoAnimal();
        tipoAnimal.setDescricao("Descrição Original");
        tipoAnimal.setRaca("Raca Original");

        when(service.encontrarPorId(1L)).thenReturn(Optional.of(tipoAnimal));
        when(service.save(any(TipoAnimal.class))).thenReturn(tipoAnimal);

        ResponseEntity<TipoAnimal> response = controller.update(1L, tipoAnimal);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipoAnimal, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarTipoAnimalNaoExistente() {
        when(service.encontrarPorId(anyLong())).thenReturn(Optional.empty());

        TipoAnimal tipoAnimal = new TipoAnimal();
        ResponseEntity<TipoAnimal> response = controller.update(99L, tipoAnimal);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarTipoAnimal() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}