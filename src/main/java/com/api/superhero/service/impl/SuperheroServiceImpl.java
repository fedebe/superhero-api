package com.api.superhero.service.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.superhero.domain.SuperheroDAO;
import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;
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
    public Superhero create(String name) throws SuperheroException {
        SuperheroDAO superhero = new SuperheroDAO(name);
        
        superhero = superheroRepository.save(superhero);
        
        Superhero result = new Superhero(superhero.getId(), superhero.getName());
       
        return result;
    }

    @Override
    public Superhero update(Superhero superhero) throws SuperheroException {
        return null;
    }

    @Override
    public void delete(Long superheroId) throws SuperheroException {
        SuperheroDAO superhero = this.findSuperheroById(superheroId);
        
        superheroRepository.delete(superhero);
    }
    
    private SuperheroDAO findSuperheroById(Long superheroId) {
        return superheroRepository.findById(superheroId).orElseThrow(() -> new SuperheroException());
    }

    @Override
    public Superhero get(Long superheroId) throws SuperheroException {
        SuperheroDAO superhero = this.findSuperheroById(superheroId);        
        
        Superhero result = new Superhero(superhero.getId(), superhero.getName());
        
        return result;
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        
        return this.getAllSuperheroes(() -> superheroRepository.findAll());
    }

    @Override
    public List<Superhero> getAllSuperheroesContainingWord(String word) throws SuperheroException {
        
        return this.getAllSuperheroes(() -> superheroRepository.findAllByNameContainingIgnoreCase(word));
    }
    
    private List<Superhero> getAllSuperheroes(Supplier<List<SuperheroDAO>> suplier) {
        List<SuperheroDAO> superheroes = suplier.get();
        
        List<Superhero> results = superheroes.stream().map(sh -> new Superhero(sh.getId(), sh.getName())).toList();
        
        return results;
    }

}
