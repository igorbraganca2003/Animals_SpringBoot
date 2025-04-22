package com.meli.animals.controllers;

import com.meli.animals.entities.Habitat;
import com.meli.animals.services.HabitatServices;
import com.meli.animals.Exceptions.HabitatNomeDuplicadoException;
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

public class HabitatControllerTest {

    @Mock
    private HabitatServices service;

    @InjectMocks
    private HabitatController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testeEncontrarTodosOsHabitats() {
        List<Habitat> habitats = Collections.singletonList(new Habitat());
        when(service.encontrarTodosHabitats()).thenReturn(habitats);

        ResponseEntity<List<Habitat>> response = controller.encontrarTodos();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(habitats, response.getBody());
    }

    @Test
    void testeRetornarNotFoundQuandoNenhumHabitatEncontrado() {
        when(service.encontrarTodosHabitats()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Habitat>> response = controller.encontrarTodos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testeEncontrarHabitatPorId() {
        Habitat habitat = new Habitat();
        when(service.encontrarPorId(1L)).thenReturn(Optional.of(habitat));

        ResponseEntity<Habitat> response = controller.encontrar(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(habitat, response.getBody());
    }

    @Test
    void testeRetornarNotFoundParaHabitatNaoExistentePorId() {
        when(service.encontrarPorId(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Habitat> response = controller.encontrar(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testeSalvarHabitat() {
        Habitat habitat = new Habitat();
        habitat.setNomeHabitat("Floresta");

        when(service.findByNomeHabitat("Floresta")).thenReturn(Optional.empty());
        when(service.save(any(Habitat.class))).thenReturn(habitat);

        ResponseEntity<Habitat> response = controller.salvarHabitat(habitat);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(habitat, response.getBody());
    }

    @Test
    void testeLancarExcecaoQuandoNomeDoHabitatForDuplicado() {
        Habitat habitat = new Habitat();
        habitat.setNomeHabitat("Floresta");

        when(service.findByNomeHabitat("Floresta")).thenReturn(Optional.of(habitat));

        HabitatNomeDuplicadoException exception = assertThrows(
                HabitatNomeDuplicadoException.class,
                () -> controller.salvarHabitat(habitat)
        );

        assertEquals("Habitat com o nome 'Floresta' já existe.", exception.getMessage());
    }

    @Test
    void testeAtualizarHabitat() {
        Habitat habitat = new Habitat();
        habitat.setNomeHabitat("Savana");

        when(service.encontrarPorId(1L)).thenReturn(Optional.of(habitat));
        when(service.save(any(Habitat.class))).thenReturn(habitat);

        habitat.setNomeHabitat("Savana Modificada");
        ResponseEntity<Habitat> response = controller.update(1L, habitat);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(habitat, response.getBody());
    }

    @Test
    void testeRetornarNotFoundAoAtualizarHabitatNaoExistente() {
        when(service.encontrarPorId(anyLong())).thenReturn(Optional.empty());

        Habitat habitat = new Habitat();
        habitat.setNomeHabitat("Savana");
        ResponseEntity<Habitat> response = controller.update(99L, habitat);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testeDeletarHabitat() {
        doNothing().when(service).deletarHabitat(1L);

        ResponseEntity<Void> response = controller.deletarHabitat(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}