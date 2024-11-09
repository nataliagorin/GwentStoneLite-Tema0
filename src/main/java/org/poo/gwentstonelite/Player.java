package org.poo.gwentstonelite;

import lombok.Getter;
import lombok.Setter;
import org.poo.gwentstonelite.cards.Card;

import java.util.ArrayList;

@Setter
@Getter
public final class Player {
    private ArrayList<ArrayList<Card>> decks;
    private ArrayList<Card> handCards;
    private ArrayList<Card> cardsToPlay;
    private Card heroCard;
    private int gamesWon;
    private int mana;

}
