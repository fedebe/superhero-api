package com.api.superhero.service.impl;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import java.util.function.Supplier;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.superhero.advise.TrackExecutionTime;
import com.api.superhero.domain.SuperheroDAO;
import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroId;
import com.api.superhero.model.SuperheroUpdate;
import com.api.superhero.repositories.SuperheroRepository;
import com.api.superhero.service.SuperheroService;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    private static final String TEMPLATE_SUPERHERO_NOT_FOUND = "Superhero with id: %s not found.";

    private static final String TEMPLATE_SUPERHERO_NAME_EXISTS = "Superhero with name: %s already exists.";
    
    private SuperheroRepository superheroRepository;

    @Autowired
    public SuperheroServiceImpl(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public Superhero create(Superhero superhero) throws SuperheroException {
        try {
            SuperheroDAO superheroDao = maptoSuperheroDAO(superhero);

            superheroDao = superheroRepository.save(superheroDao);

            return maptoSuperhero(superheroDao);
        } catch (Exception e) {
            if (e.getCause() != null
                    && e.getCause().getCause() instanceof JdbcSQLIntegrityConstraintViolationException) {
                throw new SuperheroException(e, TEMPLATE_SUPERHERO_NAME_EXISTS.formatted(superhero.name()),
                        BAD_REQUEST);
            } else {
                throw e;
            }
        }
    }

    @Override
    public Superhero update(SuperheroUpdate superhero, SuperheroId superheroId) throws SuperheroException {
        SuperheroDAO superheroDao = this.findSuperheroById(superheroId.id());
        
        try {
            superheroDao.setName(superhero.name());
            superheroDao.setSuperPowers(superhero.superPowers());
            
            superheroRepository.save(superheroDao);
            
            return maptoSuperhero(superheroDao);
        } catch (Exception e) {
            if (e.getCause() != null
                    && e.getCause().getCause() instanceof JdbcSQLIntegrityConstraintViolationException) {
                throw new SuperheroException(e, TEMPLATE_SUPERHERO_NAME_EXISTS.formatted(superhero.name()),
                        BAD_REQUEST);
            } else {
                throw e;
            }
        }
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
    @TrackExecutionTime
    public List<Superhero> getAllSuperheroesContainingWord(String word) throws SuperheroException {

        return this.getAllSuperheroes(() -> superheroRepository.findAllByNameContainingIgnoreCase(word));
    }

    private SuperheroDAO findSuperheroById(Long superheroId) {
        return superheroRepository.findById(superheroId).orElseThrow(
                () -> new SuperheroException(TEMPLATE_SUPERHERO_NOT_FOUND.formatted(superheroId), NOT_FOUND));
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
