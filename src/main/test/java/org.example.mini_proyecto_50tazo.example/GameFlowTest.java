package org.example.mini_proyecto_50tazo;

import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;
import org.example.mini_proyecto_50tazo.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for game flow and mechanics.
 *
 * @author Juan Sebastian Tapia
 */
public class GameFlowTest {

    @Test
    @DisplayName("Game initializes with correct number of players")
    public void testInitialSumAndPlayers() {
        GameUnoModel model = new GameUnoModel(1);

        assertNotNull(model.getCurrentPlayer());
        assertEquals(2, model.getPlayers().size()); // 1 human + 1 machine

        // Initial sum shouldn't be >50
        assertTrue(model.getCurrentSum() <= 50);
        assertFalse(model.isGameOver());
    }

    @Test
    @DisplayName("Game with 3 machines has 4 players total")
    public void testMultipleMachines() {
        GameUnoModel model = new GameUnoModel(3);

        assertEquals(4, model.getPlayers().size()); // 1 human + 3 machines

        // Check player names
        assertEquals("HUMAN", model.getPlayers().get(0).getName());
        assertTrue(model.getPlayers().get(1).getName().startsWith("MACHINE"));
    }

    @Test
    @DisplayName("Each player starts with 4 cards")
    public void testInitialCardDistribution() {
        GameUnoModel model = new GameUnoModel(2);

        for (Player p : model.getPlayers()) {
            assertEquals(4, p.getHand().size());
        }
    }

    @Test
    @DisplayName("Top card is set at game start")
    public void testTopCardExists() {
        GameUnoModel model = new GameUnoModel(1);

        assertNotNull(model.peekTopCard());
    }

    @Test
    @DisplayName("Current player is first player (HUMAN)")
    public void testFirstPlayerIsHuman() {
        GameUnoModel model = new GameUnoModel(2);

        assertEquals("HUMAN", model.getCurrentPlayer().getName());
    }
}