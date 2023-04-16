package com.api.superhero.service;

import java.util.List;

import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroId;
import com.api.superhero.model.SuperheroUpdate;

public interface SuperheroService {
    
    public Superhero create(Superhero superhero) throws SuperheroException;

    public Superhero update(SuperheroUpdate superhero, SuperheroId superheroId) throws SuperheroException;

    public void delete(SuperheroId superheroId) throws SuperheroException;

    public Superhero get(SuperheroId superheroId) throws SuperheroException;

    public List<Superhero> getAllSuperheroes();

    public List<Superhero> getAllSuperheroesContainingWord(String word) throws SuperheroException;

}
