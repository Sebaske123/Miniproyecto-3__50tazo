package org.example.mini_proyecto_50tazo.model.exceptions;

/**
 * Exception thrown when attempting to draw from an empty deck.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class EmptyDeckException extends RuntimeException {

    private final int cardsInDiscard;
    private final boolean recycleAttempted;

    public EmptyDeckException(String message) {
        super(message);
        this.cardsInDiscard = 0;
        this.recycleAttempted = false;
    }

    public EmptyDeckException(String message, int cardsInDiscard, boolean recycleAttempted) {
        super(message);
        this.cardsInDiscard = cardsInDiscard;
        this.recycleAttempted = recycleAttempted;
    }

    public int getCardsInDiscard() {
        return cardsInDiscard;
    }

    public boolean wasRecycleAttempted() {
        return recycleAttempted;
    }
}