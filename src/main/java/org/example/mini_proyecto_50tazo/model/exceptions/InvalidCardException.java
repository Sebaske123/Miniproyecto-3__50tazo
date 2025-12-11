package org.example.mini_proyecto_50tazo.model.exceptions;


/**
 * Exception thrown for invalid card operations (illegal play or invalid value).
 */
public class InvalidCardException extends GameException {
    public InvalidCardException(String msg) {
        super(msg);
    }
}
