package com.api.superhero.service.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.superhero.domain.SuperheroDAO;
import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroId;
import com.api.superhero.repositories.SuperheroRepository;
import com.api.superhero.service.SuperheroService;

@Service
public class SuperheroServiceImpl implements SuperheroService {
    
    private SuperheroRepository superheroRepository;
    
    @Autowired
    public SuperheroServiceImpl(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public Superhero create(Superhero superhero) throws SuperheroException {
        SuperheroDAO superheroDao = maptoSuperheroDAO(superhero);
        
        superheroDao = superheroRepository.save(superheroDao);
        
        return maptoSuperhero(superheroDao);
    }

    @Override
    public Superhero update(Superhero superhero) throws SuperheroException {
        return null;
    }

    @Override
    public void delete(SuperheroId superheroId) throws SuperheroException {
        SuperheroDAO superhero = this.findSuperheroById(superheroId.id());
        
        superheroRepository.delete(superhero);
    }

    @Override
    public Superhero get(SuperheroId superheroId) throws SuperheroException {
        SuperheroDAO superhero = this.findSuperheroById(superheroId.id());        
        
        return maptoSuperhero(superhero);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        
        return this.getAllSuperheroes(() -> superheroRepository.findAll());
    }

    @Override
    public List<Superhero> getAllSuperheroesContainingWord(String word) throws SuperheroException {
        
        return this.getAllSuperheroes(() -> superheroRepository.findAllByNameContainingIgnoreCase(word));
    }
    
    private SuperheroDAO findSuperheroById(Long superheroId) {
        return superheroRepository.findById(superheroId).orElseThrow(() -> new SuperheroException());
    }
    
    private List<Superhero> getAllSuperheroes(Supplier<List<SuperheroDAO>> suplier) {
        List<SuperheroDAO> superheroes = suplier.get();
        
        List<Superhero> results = superheroes.stream().map(sh -> maptoSuperhero(sh)).toList();
        
        return results;
    }
    
    private Superhero maptoSuperhero(SuperheroDAO superhero) {
        return new Superhero(superhero.getId(), superhero.getName(), superhero.getSuperPowers());
    }
    
    private SuperheroDAO maptoSuperheroDAO(Superhero superhero) {
        return new SuperheroDAO(superhero.name(), superhero.superPowers());
    }

}
