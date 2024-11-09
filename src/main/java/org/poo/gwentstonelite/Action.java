package org.poo.gwentstonelite;

import org.poo.fileio.CardInput;
import org.poo.gwentstonelite.cards.Card;
import org.poo.gwentstonelite.cards.heroes.EmpressThorina;
import org.poo.gwentstonelite.cards.heroes.GeneralKocioraw;
import org.poo.gwentstonelite.cards.heroes.KingMudface;
import org.poo.gwentstonelite.cards.heroes.LordRoyce;
import org.poo.gwentstonelite.cards.minions.Berserker;
import org.poo.gwentstonelite.cards.minions.Goliath;
import org.poo.gwentstonelite.cards.minions.Sentinel;
import org.poo.gwentstonelite.cards.minions.Warden;
import org.poo.gwentstonelite.cards.special_cards.Disciple;
import org.poo.gwentstonelite.cards.special_cards.Miraj;
import org.poo.gwentstonelite.cards.special_cards.TheCursedOne;
import org.poo.gwentstonelite.cards.special_cards.TheRipper;

import java.util.ArrayList;

public final class Action {

    public ArrayList<ArrayList<Card>> changeDecks(final ArrayList<ArrayList<CardInput>> decks) {
        ArrayList<ArrayList<Card>> newDecks = new ArrayList<>();
        Card newCard;

        for (ArrayList<CardInput> deck : decks) {
            ArrayList<Card> newDeck = new ArrayList<>();

            for (CardInput card : deck) {
                newCard = new Card(card);
                addCard(newCard, newDeck);
            }

            newDecks.add(newDeck);
        }

        return newDecks;
    }

    public void addCard(final Card card, final ArrayList<Card> deck) {
        switch (card.getName()) {
            case "Sentinel" :
                deck.add(new Sentinel(card));
                break;
            case "Berserker":
                deck.add(new Berserker(card));
                break;
            case "Goliath":
                deck.add(new Goliath(card));
                break;
            case "Warden":
                deck.add(new Warden(card));
                break;
            case "Miraj":
                deck.add(new Miraj(card));
                break;
            case "Disciple":
                deck.add(new Disciple(card));
                break;
            case "The Ripper":
                deck.add(new TheRipper(card));
                break;
            case "The Cursed One":
                deck.add(new TheCursedOne(card));
                break;

            default:
        }
    }

    public void setHeroForPlayer(final Player player, final CardInput hero) {
        switch (hero.getName()) {
            case "Lord Royce":
                player.setHeroCard(new LordRoyce(hero));
                break;
            case "Empress Thorina":
                player.setHeroCard(new EmpressThorina(hero));
                break;
            case "King Mudface":
                player.setHeroCard(new KingMudface(hero));
                break;
            case "General Kocioraw":
                player.setHeroCard(new GeneralKocioraw(hero));
                break;
            default:
        }
    }

    public void resetCardsFromBoard(final ArrayList<ArrayList<Card>> board,
                                    final int row1, final int row2) {

        for (Card card : board.get(row1)) {
            card.setFrozen(false);
            card.setAttacked(false);
        }

        for (Card card : board.get(row2)) {
            card.setFrozen(false);
            card.setAttacked(false);
        }

    }

    public void resetHero(final Player player) {
        player.getHeroCard().setFrozen(false);
        player.getHeroCard().setAttacked(false);
    }

    public String checkRow(final Card card) {
        return switch (card.getName()) {
            case "The Ripper", "Miraj", "Goliath", "Warden" -> "front";
            default -> "back";
        };
    }
}
