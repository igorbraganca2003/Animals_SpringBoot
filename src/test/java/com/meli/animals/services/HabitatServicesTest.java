package com.meli.animals.services;

import com.meli.animals.entities.Habitat;
import com.meli.animals.repositories.HabitatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HabitatServicesTest {

    @Mock
    private HabitatRepository habitatRepository;

    @InjectMocks
    private HabitatServiceImpl habitatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testeSalvarUmHabitat() {
        Habitat habitat = new Habitat();
        when(habitatRepository.save(any(Habitat.class))).thenReturn(habitat);

        Habitat savedHabitat = habitatService.save(habitat);
        assertEquals(habitat, savedHabitat);
        verify(habitatRepository, times(1)).save(habitat);
    }

    @Test
    void testeEncontrarTodosOsHabitats() {
        List<Habitat> habitats = Collections.singletonList(new Habitat());
        when(habitatRepository.findAll()).thenReturn(habitats);

        List<Habitat> resultado = habitatService.encontrarTodosHabitats();
        assertEquals(habitats, resultado);
        verify(habitatRepository, times(1)).findAll();
    }

    @Test
    void testeEncontrarHabitatPorNome() {
        String nomeHabitat = "Floresta";
        Optional<Habitat> habitat = Optional.of(new Habitat());
        when(habitatRepository.findByNomeHabitat(nomeHabitat)).thenReturn(habitat);

        Optional<Habitat> resultado = habitatService.findByNomeHabitat(nomeHabitat);
        assertEquals(habitat, resultado);
        verify(habitatRepository, times(1)).findByNomeHabitat(nomeHabitat);
    }

    @Test
    void testeEncontrarHabitatPorId() {
        Long id = 1L;
        Optional<Habitat> habitat = Optional.of(new Habitat());
        when(habitatRepository.findById(id)).thenReturn(habitat);

        Optional<Habitat> resultado = habitatService.encontrarPorId(id);
        assertEquals(habitat, resultado);
        verify(habitatRepository, times(1)).findById(id);
    }

    @Test
    void testeDeletarHabitat() {
        Long id = 1L;
        doNothing().when(habitatRepository).deleteById(id);

        habitatService.deletarHabitat(id);
        verify(habitatRepository, times(1)).deleteById(id);
    }
}