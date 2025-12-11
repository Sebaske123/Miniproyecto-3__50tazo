package org.example.mini_proyecto_50tazo.model.deck;

import org.example.mini_proyecto_50tazo.model.card.Card;
import org.example.mini_proyecto_50tazo.model.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck for Cincuentazo using French cards (52 cards).
 * Values: 2-10, J, Q, K, A
 * Suits: H (Hearts), D (Diamonds), C (Clubs), S (Spades)
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2025
 */
public class Deck {

    private final List<Card> deckOfCards = new ArrayList<>();

    /**
     * Creates a standard French deck with 52 cards.
     */
    public Deck() {
        String[] suits = {"H", "D", "C", "S"}; // Hearts, Diamonds, Clubs, Spades
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String value : values) {
                deckOfCards.add(new Card(value, suit));
            }
        }
    }

    /**
     * Shuffles the deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(deckOfCards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return the card drawn from the deck, or null if empty
     */
    public Card draw() {
        if (deckOfCards.isEmpty()) {
            return null;
        }
        return deckOfCards.remove(0);
    }

    /**
     * Draws a card from the top of the deck.
     * Throws exception if deck is empty.
     *
     * @return the card drawn from the deck
     * @throws EmptyDeckException if the deck is empty
     */
    public Card drawCard() throws EmptyDeckException {
        if (deckOfCards.isEmpty()) {
            throw new EmptyDeckException(
                    "Cannot draw from empty deck",
                    0,
                    false
            );
        }
        return deckOfCards.remove(0);
    }

    /**
     * Adds a card to the bottom of the deck.
     *
     * @param card the card to add
     */
    public void addToBottom(Card card) {
        if (card != null) {
            deckOfCards.add(card);
        }
    }

    /**
     * Recycles the deck when it's empty.
     * This method can be called to shuffle discarded cards back into the deck.
     */
    public void recycleFromEmpty() {
        if (!deckOfCards.isEmpty()) {
            shuffle();
        }
    }

    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck has no cards
     */
    public boolean isEmpty() {
        return deckOfCards.isEmpty();
    }

    /**
     * Gets the number of cards remaining in the deck.
     *
     * @return the number of cards
     */
    public int size() {
        return deckOfCards.size();
    }

    /**
     * Gets all cards in the deck (for testing purposes).
     *
     * @return unmodifiable list of cards
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(deckOfCards);
    }
}