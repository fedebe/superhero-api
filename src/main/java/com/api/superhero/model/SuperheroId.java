package com.api.superhero.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuperheroId(@JsonProperty("superheroId") Long id) {

}
