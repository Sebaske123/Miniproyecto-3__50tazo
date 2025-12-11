package org.example.mini_proyecto_50tazo.model.exceptions;

/**
 * Exception thrown when a player is eliminated due to having no playable cards.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class PlayerEliminatedException extends GameException {

    private final String playerName;
    private final int cardsInHand;
    private final int currentTableSum;

    public PlayerEliminatedException(String msg) {
        super(msg);
        this.playerName = "Unknown";
        this.cardsInHand = 0;
        this.currentTableSum = 0;
    }

    public PlayerEliminatedException(String msg, String playerName, int cardsInHand, int currentTableSum) {
        super(msg);
        this.playerName = playerName;
        this.cardsInHand = cardsInHand;
        this.currentTableSum = currentTableSum;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCardsInHand() {
        return cardsInHand;
    }

    public int getCurrentTableSum() {
        return currentTableSum;
    }
}