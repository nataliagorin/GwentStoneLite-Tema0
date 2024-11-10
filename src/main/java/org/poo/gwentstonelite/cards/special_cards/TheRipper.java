package org.poo.gwentstonelite.cards.special_cards;

import org.poo.fileio.ActionsInput;
import org.poo.gwentstonelite.GameSession;
import org.poo.gwentstonelite.GwentStoneLite;
import org.poo.gwentstonelite.cards.Card;

public final class TheRipper extends Card {
    public TheRipper(final Card card) {
        super(card);
    }


    @Override
    public void useAbility(final GameSession game, final ActionsInput action) {
        String errorMessage = "null";
        Card cardAttacked = game.getBoard().get(action.getCardAttacked().getX()).
                get(action.getCardAttacked().getY());

        if (action.getCardAttacker().getX() == 1) {
            if (action.getCardAttacked().getX() == GwentStoneLite.ROW2
                    || action.getCardAttacked().getX() == GwentStoneLite.ROW3) {
                if (checkTanksOnRow(game, GwentStoneLite.ROW2)) {
                    if (GwentStoneLite.getCardActions().isTank(cardAttacked)) {
                        ability(game, action);
                    } else {
                        errorMessage = "Attacked card is not of type 'Tank'.";
                    }
                } else {
                    ability(game, action);
                }
            } else {
                errorMessage = "Attacked card does not belong to the enemy.";
            }
        } else {
            if (action.getCardAttacked().getX() == GwentStoneLite.ROW0
                    || action.getCardAttacked().getX() == GwentStoneLite.ROW1) {
                if (checkTanksOnRow(game, GwentStoneLite.ROW1)) {
                    if (GwentStoneLite.getCardActions().isTank(cardAttacked)) {
                        ability(game, action);
                    } else {
                        errorMessage = "Attacked card is not of type 'Tank'.";
                    }
                } else {
                    ability(game, action);
                }
            } else {
                errorMessage = "Attacked card does not belong to the enemy.";
            }
        }

        if (!errorMessage.equals("null")) {
            GwentStoneLite.getOutputCreator().cardUsesAttackError(errorMessage, action);
        }
    }

    /**
     * Checks if there are any tank-type cards on the specified row of the game board.
     *
     * @param game The current game session, providing access to the game board and cards.
     * @param row The index of the row on the board to check for tank-type cards.
     * @return true if there is at least one tank card on the specified row,
     *         false otherwise.
     * This method iterates over all cards on the given row of the board and checks each card
     * using the {isTank method from CardActions to determine if it is a tank.
     */
    public boolean checkTanksOnRow(final GameSession game, final int row) {
        for (Card card : game.getBoard().get(row)) {
            if (GwentStoneLite.getCardActions().isTank(card)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Applies the ability of The Ripper to reduce the attack damage to a target card.
     * This ability targets a specified card on the game board and decreases its
     * attack damage by 2 points. If the resulting attack damage is less than 0,
     * it is set to 0 to prevent negative values.
     *
     * @param game The current game session, which provides access to the game board
     *             and players involved.
     * @param action The action input specifying the target card's position.
     */
    public void ability(final GameSession game, final ActionsInput action) {
        Card cardAttacked = game.getBoard().get(action.getCardAttacked().getX()).
                get(action.getCardAttacked().getY());

        cardAttacked.setAttackDamage(cardAttacked.getAttackDamage() - 2);

        if (cardAttacked.getAttackDamage() < 0) {
            cardAttacked.setAttackDamage(0);
        }

        setAttacked(true);
    }
}
