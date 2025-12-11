package org.example.mini_proyecto_50tazo.model.exceptions;


/**
 * Exception thrown when a player exceeds the limit of 50 on the table sum.
 */
public class GameFiftyException extends GameException {
    public GameFiftyException(String msg) {
        super(msg);
    }
}
