package com.api.superhero.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record Superhero(@JsonProperty("id") Long id,
                        @NotBlank(message = "Name field needed to be completed.") 
                        @JsonProperty("name") String name,
                        @JsonProperty("superPowers") String[] superPowers) {

}
