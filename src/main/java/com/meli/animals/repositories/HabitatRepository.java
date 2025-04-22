package com.meli.animals.repositories;

import com.meli.animals.entities.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
    Optional<Habitat> findByNomeHabitat(String nomeHabitat);
}