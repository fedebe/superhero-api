package com.api.superhero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.superhero.documentation.SuperheroResources;
import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroId;
import com.api.superhero.service.SuperheroService;

@RestController
@RequestMapping("/api")
public class SuperheroController implements SuperheroResources {
    
    private SuperheroService superheroService;
    
    @Autowired
    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @PostMapping("/superheroes")
    public Superhero create(@RequestBody Superhero superhero) {
        return superheroService.create(superhero);
    }

    @PatchMapping("/superheroes/{superheroId}")
    public Superhero update(@RequestBody Superhero superhero, @PathVariable("superheroId") SuperheroId superheroId) {
        return superheroService.update(superhero);
    }

    @DeleteMapping("/superheroes/{superheroId}")
    public void delete(SuperheroId superheroId) {
        superheroService.delete(superheroId);
    }

    @GetMapping("/superheroes/{superheroId}")
    public Superhero get(@PathVariable("superheroId") SuperheroId superheroId) {
        return superheroService.get(superheroId);
    }

    @GetMapping("/superheroes")
    public List<Superhero> getAllSuperheroes() {
        return superheroService.getAllSuperheroes();
    }

    @GetMapping("/superheroes/by-word")
    public List<Superhero> getAllSuperheroesContainingWord(@RequestParam("byWord") String word) {
        return superheroService.getAllSuperheroesContainingWord(word);
    }

}
