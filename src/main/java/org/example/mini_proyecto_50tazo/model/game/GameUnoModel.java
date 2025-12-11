package org.example.mini_proyecto_50tazo.model.game;

import org.example.mini_proyecto_50tazo.model.card.Card;
import org.example.mini_proyecto_50tazo.model.deck.Deck;
import org.example.mini_proyecto_50tazo.model.exceptions.PlayerEliminatedException;
import org.example.mini_proyecto_50tazo.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game model for Cincuentazo.
 * Manages game state, turn flow, and player elimination.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class GameUnoModel {

    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck();
    private int turn = 0;
    private Card topCard;
    private int currentSum = 0;
    private boolean gameOver = false;
    private Player winner;

    /**
     * Creates a new game with specified number of machine players.
     *
     * @param machineCount number of machine opponents (1-3)
     */
    public GameUnoModel(int machineCount) {
        // Add human player
        players.add(new Player("HUMAN"));

        // Add machine players
        for (int i = 1; i <= machineCount; i++) {
            players.add(new Player("MACHINE-" + i));
        }

        deck.shuffle();

        // Deal 4 cards to each player
        for (Player p : players) {
            for (int i = 0; i < 4; i++) {
                Card card = deck.draw();
                if (card != null) {
                    p.addCard(card);
                }
            }
        }

        // Initial card on table
        topCard = deck.draw();
        if (topCard != null) {
            currentSum = topCard.getValueForInitial();
            // Make sure initial sum is not negative (minimum 0)
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
    }

    /**
     * Attempts to play a card for the given player.
     *
     * @param player the player attempting to play
     * @param card the card to play
     * @return true if successful
     * @throws Exception if the play is invalid
     */
    public synchronized boolean playCard(Player player, Card card) throws Exception {
        if (gameOver) {
            throw new Exception("Game is over");
        }

        if (player != getCurrentPlayer()) {
            throw new Exception("Not your turn");
        }

        if (!player.getHand().contains(card)) {
            throw new Exception("Card not in hand");
        }

        // Calculate new sum
        int value = card.resolveValueForPlay(currentSum);
        int newSum = currentSum + value;

        // CORRECTED: Allow negative values (J, Q, K subtract 10)
        // Only prevent sum from going BELOW 0 or ABOVE 50
        if (newSum < 0) {
            newSum = 0; // Clamp to 0 instead of throwing error
        }

        if (newSum > 50) {
            throw new Exception("Card would exceed 50");
        }

        // Play the card
        currentSum = newSum;
        topCard = card;
        player.removeCard(card);

        // Draw a card
        Card drawn = deck.draw();
        if (drawn != null) {
            player.addCard(drawn);
        } else {
            // Try to recycle deck if needed
            deck.recycleFromEmpty();
            drawn = deck.draw();
            if (drawn != null) {
                player.addCard(drawn);
            }
        }

        // Check if player has any playable cards left
        boolean hasPlayableCard = false;
        for (Card c : player.getHand()) {
            if (c.canBePlayed(currentSum)) {
                hasPlayableCard = true;
                break;
            }
        }

        // If no playable cards, eliminate player
        if (!hasPlayableCard && player.getHand().size() > 0) {
            eliminatePlayer(player);
            throw new PlayerEliminatedException(
                    "Player eliminated",
                    player.getName(),
                    player.getHand().size(),
                    currentSum
            );
        }

        nextTurn();
        return true;
    }

    /**
     * Allows a player to draw a card.
     *
     * @param player the player drawing
     */
    public synchronized void drawCard(Player player) {
        if (gameOver) {
            System.out.println("⚠️ Game is over, cannot draw");
            return;
        }

        if (player != getCurrentPlayer()) {
            System.out.println("⚠️ Not " + player.getName() + "'s turn, cannot draw");
            return;
        }

        Card drawn = deck.draw();
        if (drawn != null) {
            player.addCard(drawn);
            System.out.println("✅ " + player.getName() + " drew a card: " + drawn);
        } else {
            System.out.println("⚠️ Deck is empty");
        }

        nextTurn();
    }

    /**
     * Eliminates a player from the game.
     *
     * @param player the player to eliminate
     */
    private void eliminatePlayer(Player player) {
        // Return cards to deck
        for (Card c : player.getHand()) {
            deck.addToBottom(c);
        }
        player.clearHand();
        players.remove(player);

        // Check if game is over
        if (players.size() == 1) {
            gameOver = true;
            winner = players.get(0);
        }

        // Adjust turn if needed
        if (turn >= players.size() && players.size() > 0) {
            turn = 0;
        }
    }

    /**
     * Advances to the next player's turn.
     */
    private void nextTurn() {
        if (players.size() > 0) {
            turn = (turn + 1) % players.size();
        }
    }

    // Getters
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty()) return null;
        return players.get(turn);
    }

    public Card peekTopCard() {
        return topCard;
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }
}