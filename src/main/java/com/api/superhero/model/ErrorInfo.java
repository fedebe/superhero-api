package com.api.superhero.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorInfo(@JsonProperty("messages") String[] messages, 
                        @JsonProperty("status_code") int statusCode, 
                        @JsonProperty("uri") String uriRequested) {

}
