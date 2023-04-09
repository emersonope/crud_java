package com.br.people.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PeopleAlreadyExistsException extends RuntimeException {
    public PeopleAlreadyExistsException(String message) {
        super(message);
    }
}




