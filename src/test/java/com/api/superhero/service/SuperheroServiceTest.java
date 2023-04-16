package com.api.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;
import com.api.superhero.repositories.SuperheroRepository;
import com.api.superhero.service.impl.SuperheroServiceImpl;

import jakarta.validation.ConstraintViolationException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class SuperheroServiceTest {
    
    @Autowired
    private SuperheroRepository superheroRepository;
    
    private SuperheroServiceImpl superheroService;
    
    @BeforeAll
    public void init() {
        superheroService = new SuperheroServiceImpl(superheroRepository);
    }
    
    @Test
    public void createSuperheroAndSavedOk() {
        Superhero superhero = new Superhero(null,"Superman", new String[] { "Fliying" });

        superhero = superheroService.create(superhero);
        
        
        assertTrue(superhero.id() != null);
        assertEquals("Superman", superhero.name());
        assertEquals("Fliying", superhero.superPowers()[0]);
    }
    
    @Test
    public void createSuperheroAndNameAlreadyExists() {
        Superhero superhero = new Superhero(null,"Superman", new String[] { "Fliying" });
        superheroService.create(superhero);
        
        Exception exception = assertThrows(SuperheroException.class, () -> superheroService.create(superhero));
        
        assertTrue(exception.getMessage().contains("Superhero with name: Superman already exists."));
    }
    
    @Test
    public void createSuperheroWithOutName() {
        Superhero superhero = new Superhero(null,"", new String[] { "Fliying" });
        
        Exception exception = assertThrows(ConstraintViolationException.class, () -> superheroService.create(superhero));
        
        assertTrue(exception.getMessage().contains("Name field needed to be completed."));
    }

}
