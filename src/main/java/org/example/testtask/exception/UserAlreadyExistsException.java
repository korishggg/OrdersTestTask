package org.example.testtask.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String email;

    public UserAlreadyExistsException(String email) {
        this.email = email;
    }

}
