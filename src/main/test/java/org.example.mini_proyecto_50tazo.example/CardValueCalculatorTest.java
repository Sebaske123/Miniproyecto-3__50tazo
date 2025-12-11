package org.example.mini_proyecto_50tazo;

import org.example.mini_proyecto_50tazo.model.card.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Card value calculation in Cincuentazo.
 *
 * @author Juan Sebastian Tapia
 */
public class CardValueCalculatorTest {

    @Test
    @DisplayName("Number card has correct value")
    public void testNumberCard() {
        Card c = new Card("5");
        assertTrue(c.canBePlayed(0));
        assertEquals(5, c.resolveValueForPlay(0));
    }

    @Test
    @DisplayName("Face card (J, Q, K) has value -10")
    public void testFaceCard() {
        Card j = new Card("J");
        assertEquals(-10, j.resolveValueForPlay(0));

        Card q = new Card("Q");
        assertEquals(-10, q.resolveValueForPlay(0));

        Card k = new Card("K");
        assertEquals(-10, k.resolveValueForPlay(0));
    }

    @Test
    @DisplayName("Nine has value 0")
    public void testNine() {
        Card nine = new Card("9");
        assertEquals(0, nine.resolveValueForPlay(0));
        assertTrue(nine.canBePlayed(50)); // Can always play 9
    }

    @Test
    @DisplayName("Ace takes optimal value (10 or 1)")
    public void testAceTakes10WhenPossible() {
        Card a = new Card("A");

        // If current sum 30, adding 10 still <=50, so choose 10
        assertEquals(10, a.resolveValueForPlay(30));

        // If current sum 45, adding 10 would exceed 50 -> pick 1
        assertEquals(1, a.resolveValueForPlay(45));

        // If current sum 40, can still add 10
        assertEquals(10, a.resolveValueForPlay(40));

        // If current sum 41, must use 1
        assertEquals(1, a.resolveValueForPlay(41));
    }

    @Test
    @DisplayName("Card playability check")
    public void testCardPlayability() {
        Card five = new Card("5");

        assertTrue(five.canBePlayed(0));
        assertTrue(five.canBePlayed(45));
        assertFalse(five.canBePlayed(46)); // 46 + 5 = 51 > 50
        assertFalse(five.canBePlayed(50));
    }
}