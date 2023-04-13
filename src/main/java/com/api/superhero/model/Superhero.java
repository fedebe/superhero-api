package com.api.superhero.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Superhero(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

}
