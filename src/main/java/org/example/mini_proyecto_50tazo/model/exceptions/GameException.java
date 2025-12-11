package org.example.mini_proyecto_50tazo.model.exceptions;


/**
 * Base exception for game-related errors in Cincuentazo.
 */
public class GameException extends Exception {
    public GameException(String msg) {
        super(msg);
    }
}
