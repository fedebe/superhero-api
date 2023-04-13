package com.api.superhero.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.superhero.documentation.SuperheroResources;
import com.api.superhero.model.Superhero;

@RestController
public class SuperheroController implements SuperheroResources {

    @PostMapping("/superheroes")
    public Superhero create(@RequestBody String name) {
        return null;
    }

    @PatchMapping("/superheroes/{superheroId}")
    public Superhero update(@RequestBody Superhero superhero, @PathVariable("superheroId") Long superheroId) {
        return null;
    }

    @DeleteMapping("/superheroes/{superheroId}")
    public void delete(Long superheroId) {
    }

    @GetMapping("/superheroes/{superheroId}")
    public Superhero get(@PathVariable("superheroId") Long superheroId) {
        return null;
    }

    @GetMapping("/superheroes")
    public List<Superhero> getAllSuperheroes() {
        return null;
    }

    @GetMapping("/superheroes/{byWord}")
    public List<Superhero> getAllSuperheroesContainingWord(@PathVariable("byWord") String word) {
        return null;
    }

}
