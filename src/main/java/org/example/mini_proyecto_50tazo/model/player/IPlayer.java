package org.example.mini_proyecto_50tazo.model.player;

import org.example.mini_proyecto_50tazo.model.card.Card;
import java.util.List;

public interface IPlayer {
    String getName();
    List<Card> getHand();
    boolean hasCards();
    void addCard(Card c);
}
