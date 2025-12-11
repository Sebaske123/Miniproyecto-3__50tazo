package org.example.mini_proyecto_50tazo.model.table;


import org.example.mini_proyecto_50tazo.model.card.Card;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Optional table model to store cards visually.
 * Not strictly required, since GameUnoModel already manages the stack.
 */
public class Table {

    private final Deque<Card> pile = new ArrayDeque<>();

    public void placeCard(Card card) {
        pile.push(card);
    }

    public Card peekTop() {
        return pile.peek();
    }

    public int size() {
        return pile.size();
    }

    public Card pop() {
        return pile.pop();
    }
}
