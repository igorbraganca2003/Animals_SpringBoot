package com.meli.animals.Exceptions;

public class HabitatNomeDuplicadoException extends RuntimeException {
    public HabitatNomeDuplicadoException(String nomeHabitat) {
        super("Já existe um habitat com o nome: " + nomeHabitat);
    }
}