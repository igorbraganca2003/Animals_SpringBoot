package com.meli.animals.services;

import com.meli.animals.entities.TipoAnimal;
import com.meli.animals.repositories.TipoAnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TipoAnimalServiceTest {

    @Mock
    private TipoAnimalRepository tipoAnimalRepository;

    @InjectMocks
    private TipoAnimalServiceImpl tipoAnimalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testeSalvarTipoAnimal() {
        TipoAnimal tipoAnimal = new TipoAnimal();
        when(tipoAnimalRepository.save(any(TipoAnimal.class))).thenReturn(tipoAnimal);

        TipoAnimal tipoAnimalSalvo = tipoAnimalService.save(tipoAnimal);
        assertEquals(tipoAnimal, tipoAnimalSalvo);
        verify(tipoAnimalRepository, times(1)).save(tipoAnimal);
    }

    @Test
    void testeEncontrarTodosTipos() {
        List<TipoAnimal> tipos = Collections.singletonList(new TipoAnimal());
        when(tipoAnimalRepository.findAll()).thenReturn(tipos);

        List<TipoAnimal> resultado = tipoAnimalService.encontrarTodosTipos();
        assertEquals(tipos, resultado);
        verify(tipoAnimalRepository, times(1)).findAll();
    }

    @Test
    void testeEncontrarPorRaca() {
        String raca = "Labrador";
        List<TipoAnimal> tipos = Collections.singletonList(new TipoAnimal());
        when(tipoAnimalRepository.findByRaca(raca)).thenReturn(tipos);

        List<TipoAnimal> resultado = tipoAnimalService.findByRaca(raca);
        assertEquals(tipos, resultado);
        verify(tipoAnimalRepository, times(1)).findByRaca(raca);
    }

    @Test
    void testeEncontrarPorId() {
        Long id = 1L;
        Optional<TipoAnimal> tipoAnimal = Optional.of(new TipoAnimal());
        when(tipoAnimalRepository.findById(id)).thenReturn(tipoAnimal);

        Optional<TipoAnimal> resultado = tipoAnimalService.encontrarPorId(id);
        assertEquals(tipoAnimal, resultado);
        verify(tipoAnimalRepository, times(1)).findById(id);
    }

    @Test
    void testeDeletarTipoAnimal() {
        Long id = 1L;
        doNothing().when(tipoAnimalRepository).deleteById(id);

        tipoAnimalService.delete(id);
        verify(tipoAnimalRepository, times(1)).deleteById(id);
    }
}