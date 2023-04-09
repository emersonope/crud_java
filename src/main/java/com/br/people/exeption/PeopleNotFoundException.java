package com.br.people.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PeopleNotFoundException extends RuntimeException {

    public PeopleNotFoundException(String message) {
        super(message);
    }
}
