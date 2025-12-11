package org.example.mini_proyecto_50tazo.model.player;

import org.example.mini_proyecto_50tazo.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private final String name;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    public void addCard(Card c) {
        if (c != null) hand.add(c);
    }

    public void removeCard(Card c) {
        hand.remove(c);
    }

    public void clearHand() {
        hand.clear();
    }
}
