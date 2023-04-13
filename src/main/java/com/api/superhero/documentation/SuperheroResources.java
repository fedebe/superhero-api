package com.api.superhero.documentation;

import java.util.List;

import com.api.superhero.model.Superhero;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Superhero API", description = "These endpoints allow doing CRUD operations over superheroes.")
public interface SuperheroResources {

    @Operation(summary = "Create a new superhero.")
    public Superhero create(@Parameter(name = "name",
            description = "Name of the superhero.") String name);

    @Operation(summary = "Update a superhero by id.")
    public Superhero update(@Parameter(name = "Superhero",
            description = "Superhero data to be updated.") Superhero superhero,
            @Parameter(name = "superheroId", in = ParameterIn.PATH,
                    description = "Id of the superheroe.") Long superheroId);

    @Operation(summary = "Remove a superhero by id.")
    public void delete(@Parameter(name = "superheroId", in = ParameterIn.PATH,
            description = "Id of the superheroe.") Long superheroId);

    @Operation(summary = "Returns a superhero by id.")
    public Superhero get(@Parameter(name = "superheroId", in = ParameterIn.PATH,
            description = "Id of the superheroe that is needed.") Long superheroId);

    @Operation(summary = "Returns all superheroes.")
    public List<Superhero> getAllSuperheroes();

    @Operation(summary = "Returns all superheroes that contain the word passed by path parameter.")
    public List<Superhero> getAllSuperheroesContainingWord(@Parameter(name = "byWord", in = ParameterIn.PATH,
            description = "A word that is used as a filter, to retrieve all superheroes that contain that word.") String word);

}
