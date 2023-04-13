package com.api.superhero.service;

import java.util.List;

import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;

public interface SuperheroService {
    
    public Superhero create(String name) throws SuperheroException;

    public Superhero update(Superhero superhero) throws SuperheroException;

    public void delete(Long superheroId) throws SuperheroException;

    public Superhero get(Long superheroId) throws SuperheroException;

    public List<Superhero> getAllSuperheroes();

    public List<Superhero> getAllSuperheroesContainingWord(String word) throws SuperheroException;

}
