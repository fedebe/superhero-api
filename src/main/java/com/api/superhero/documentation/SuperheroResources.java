package com.api.superhero.documentation;

import java.util.List;

import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroId;
import com.api.superhero.model.SuperheroUpdate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Superhero API", description = "These endpoints allow doing CRUD operations over superheroes.")
public interface SuperheroResources {

    @Operation(summary = "Create a new superhero.")
    public Superhero create(@Parameter(name = "superhero",
            description = "Name of the superhero.") Superhero supperhero);

    @Operation(summary = "Update a superhero by id.")
    public Superhero update(@Parameter(name = "Superhero",
            description = "Superhero data to be updated.") SuperheroUpdate superhero,
            @Parameter(name = "superheroId", in = ParameterIn.PATH,
                    description = "Id of the superheroe.") SuperheroId superheroId);

    @Operation(summary = "Remove a superhero by id.")
    public void delete(@Parameter(name = "superheroId", in = ParameterIn.PATH,
            description = "Id of the superheroe.") SuperheroId superheroId);

    @Operation(summary = "Returns a superhero by id.")
    public Superhero get(@Parameter(name = "superheroId", in = ParameterIn.PATH,
            description = "Id of the superheroe that is needed.") SuperheroId superheroId);

    @Operation(summary = "Returns all superheroes.")
    public List<Superhero> getAllSuperheroes();

    @Operation(summary = "Returns all superheroes that contain the word passed by path parameter.")
    public List<Superhero> getAllSuperheroesContainingWord(@Parameter(name = "byWord", in = ParameterIn.QUERY,
            description = "A word that is used as a filter, to retrieve all superheroes that contain that word.") String word);

}
