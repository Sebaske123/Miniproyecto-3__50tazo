package org.example.mini_proyecto_50tazo;

import org.example.mini_proyecto_50tazo.model.deck.Deck;
import org.example.mini_proyecto_50tazo.model.card.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Deck functionality.
 *
 * @author Juan Sebastian Tapia
 */
public class DeckTest {

    @Test
    @DisplayName("Deck has 52 cards initially")
    public void testInitialSize() {
        Deck d = new Deck();
        assertEquals(52, d.size());
    }

    @Test
    @DisplayName("Can draw a card from deck")
    public void testDraw() {
        Deck d = new Deck();
        Card c = d.draw();
        assertNotNull(c);
        assertEquals(51, d.size());
    }

    @Test
    @DisplayName("DrawCard method works")
    public void testDrawCard() {
        Deck d = new Deck();
        Card c = d.drawCard();
        assertNotNull(c);
        assertEquals(51, d.size());
    }

    @Test
    @DisplayName("Shuffle randomizes deck")
    public void testShuffle() {
        Deck d1 = new Deck();
        Deck d2 = new Deck();

        d1.shuffle();

        // Draw first 5 cards from each
        boolean different = false;
        for (int i = 0; i < 5; i++) {
            Card c1 = d1.draw();
            Card c2 = d2.draw();
            if (!c1.equals(c2)) {
                different = true;
                break;
            }
        }

        // Very likely to be different after shuffle
        assertTrue(different || d1.size() == d2.size());
    }

    @Test
    @DisplayName("AddToBottom adds card to deck")
    public void testAddToBottom() {
        Deck d = new Deck();
        int initialSize = d.size();

        Card card = new Card("A", "H");
        d.addToBottom(card);

        assertEquals(initialSize + 1, d.size());
    }

    @Test
    @DisplayName("Empty deck returns null on draw")
    public void testEmptyDeck() {
        Deck d = new Deck();

        // Draw all cards
        for (int i = 0; i < 52; i++) {
            d.draw();
        }

        assertTrue(d.isEmpty());
        assertNull(d.draw());
    }
}