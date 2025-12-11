package org.example.mini_proyecto_50tazo.model.machine;

import org.example.mini_proyecto_50tazo.model.card.Card;
import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;
import org.example.mini_proyecto_50tazo.model.player.Player;

/**
 * Thread for machine player AI.
 * Automatically plays cards when it's the machine's turn.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class ThreadMachinePlayer implements Runnable {

    private final GameUnoModel model;
    private final Player cpu;

    /**
     * Creates a new machine player thread.
     *
     * @param model the game model
     * @param cpu the machine player
     */
    public ThreadMachinePlayer(GameUnoModel model, Player cpu) {
        this.model = model;
        this.cpu = cpu;
    }

    @Override
    public void run() {
        while (!model.isGameOver()) {
            try {
                synchronized (model) {
                    // Check if it's this machine's turn
                    if (model.getCurrentPlayer() != cpu) {
                        // Not our turn, wait a bit
                        Thread.sleep(100);
                        continue;
                    }

                    // Find a playable card
                    Card playableCard = null;
                    int currentSum = model.getCurrentSum();

                    for (Card card : cpu.getHand()) {
                        if (card.canBePlayed(currentSum)) {
                            playableCard = card;
                            break;
                        }
                    }

                    // Try to play the card
                    if (playableCard != null) {
                        try {
                            model.playCard(cpu, playableCard);
                            System.out.println(cpu.getName() + " played: " + playableCard);
                        } catch (Exception e) {
                            System.out.println(cpu.getName() + " couldn't play: " + e.getMessage());
                        }
                    } else {
                        // No playable card, draw one
                        try {
                            model.drawCard(cpu);
                            System.out.println(cpu.getName() + " drew a card");
                        } catch (Exception e) {
                            System.out.println(cpu.getName() + " couldn't draw: " + e.getMessage());
                        }
                    }
                }

                // Wait before next action
                Thread.sleep(800);

            } catch (InterruptedException e) {
                System.out.println(cpu.getName() + " thread interrupted");
                return;
            } catch (Exception e) {
                System.out.println(cpu.getName() + " error: " + e.getMessage());
            }
        }

        System.out.println(cpu.getName() + " thread finished");
    }
}