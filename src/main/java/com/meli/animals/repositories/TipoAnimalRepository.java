package com.meli.animals.repositories;

import com.meli.animals.entities.TipoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoAnimalRepository extends JpaRepository<TipoAnimal, Long> {
}
