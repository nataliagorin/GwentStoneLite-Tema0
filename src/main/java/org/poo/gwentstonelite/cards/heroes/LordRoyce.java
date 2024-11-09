package org.poo.gwentstonelite.cards.heroes;

import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.gwentstonelite.GameSession;
import org.poo.gwentstonelite.GwentStoneLite;
import org.poo.gwentstonelite.cards.Card;

import java.util.ArrayList;


public final class LordRoyce extends Card {
    public LordRoyce(final CardInput card) {
        super(card);
    }

    @Override
    public void useAbility(final GameSession game, final ActionsInput action) {
        if (game.getPlayerTurn() == 1) {
            if (action.getAffectedRow() == GwentStoneLite.ROW0
                    || action.getAffectedRow() == GwentStoneLite.ROW1) {
                ability(game, action);
            } else {
                GwentStoneLite.getOutputCreator().
                        useHeroAbilityError("Selected row does not belong to the enemy.", action);
            }
        } else {
            if (action.getAffectedRow() == GwentStoneLite.ROW2
                    || action.getAffectedRow() == GwentStoneLite.ROW3) {
                ability(game, action);
            } else {
                GwentStoneLite.getOutputCreator().
                        useHeroAbilityError("Selected row does not belong to the enemy.", action);
            }
        }
    }

    public void ability(final GameSession game, final ActionsInput action) {
        ArrayList<Card> cards = game.getBoard().get(action.getAffectedRow());

        for (Card card : cards) {
            card.setFrozen(true);
        }

        setAttacked(true);

        if (game.getPlayerTurn() == 1) {
            game.getPlayerOne().setMana(game.getPlayerOne().getMana() - getMana());
        } else {
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana() - getMana());
        }
    }
}
