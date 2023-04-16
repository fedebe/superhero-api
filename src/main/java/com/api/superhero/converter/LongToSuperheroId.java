package com.api.superhero.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.superhero.exception.SuperheroException;
import com.api.superhero.model.SuperheroId;
import static org.springframework.http.HttpStatus.BAD_REQUEST;;

@Component
public class LongToSuperheroId implements Converter<String, SuperheroId> {

    @Override
    public SuperheroId convert(String from) {
        try {
            return new SuperheroId(Long.valueOf(from));
        } catch (NumberFormatException e) {
            throw new SuperheroException(e, "Path variable must be a number.", BAD_REQUEST);
        }
    }
}
