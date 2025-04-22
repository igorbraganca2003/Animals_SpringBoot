package com.meli.animals.services;

import com.meli.animals.entities.Habitat;
import com.meli.animals.repositories.HabitatRepository;
import com.meli.animals.services.HabitatServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HabitatServiceImpl implements HabitatServices {

    private final HabitatRepository habitatRepository;

    @Override
    public Habitat save(Habitat habitat) {
        return habitatRepository.save(habitat);
    }

    @Override
    public List<Habitat> encontrarTodosHabitats() {
        return habitatRepository.findAll();
    }

    @Override
    public List<Habitat> findByNomeHabitat(String nomeHabitat) {
        return habitatRepository.findByNomeHabitat(nomeHabitat);
    }

    @Override
    public Optional<Habitat> encontrarPorId(Long id) {
        return habitatRepository.findById(id);
    }

    @Override
    public void deletarHabitat(Long id) {
        habitatRepository.deleteById(id);
    }
}
