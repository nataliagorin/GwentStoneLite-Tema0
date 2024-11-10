package org.poo.gwentstonelite.cards;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.gwentstonelite.GameSession;
import org.poo.gwentstonelite.GwentStoneLite;

import java.util.ArrayList;

@Setter
public class Card {
    @Getter
    private int mana;
    @Getter
    private int attackDamage;
    @Getter
    private int health;
    @Getter
    private String description;
    @Getter
    private ArrayList<String> colors;
    @Getter
    private String name;
    @Getter
    private boolean frozen;
    private boolean attacked;

    public Card() {
    }

    public Card(final Card card) {
        this.mana = card.mana;
        this.attackDamage = card.attackDamage;
        this.health = card.health;
        this.description = card.description;
        this.colors = card.colors;
        this.name = card.name;
        this.frozen = card.frozen;
        this.attacked = card.attacked;
    }

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.frozen = false;
        this.attacked = false;
    }

    /**
     * Returns whether the card has attacked.
     * This method checks the current state of the card
     * to determine if it has already attacked during the turn.
     *
     * @return true if the card has attacked, fals otherwise.
     */
    public boolean hasAttacked() {
        return attacked;
    }

    /**
     * Executes an attack using the card's attack damage. The attack can either target
     * another card or the enemy's hero, depending on the action performed.
     * If the action is to attack another card, the method
     * reduces the health of the targeted card by the attack damage.
     * If the targeted card's health drops to zero or below,
     * the card is removed from the board.
     * If the action targets a hero, the hero's health is reduced
     * by the attacking card's damage, and the attacker is marked as having attacked.
     * If a hero's health reaches zero or below, the game ends,
     * and the player who killed the enemy hero is declared the winner.
     *
     * @param game the GameSession object that holds the current state of the game,
     *             including the board and players.
     * @param action the ActionsInput object containing details
     *               of the action, including the target card and attacker.
     */
    public void useAttack(final GameSession game, final ActionsInput action) {
        if (action.getCommand().equals("cardUsesAttack")) {
            Card cardAttacked = game.getBoard().
                    get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY());

            cardAttacked.setHealth(cardAttacked.getHealth() - attackDamage);

            if (cardAttacked.getHealth() <= 0) {
                game.getBoard().get(action.getCardAttacked()
                        .getX()).remove(action.getCardAttacked().getY());
            }

            attacked = true;
        } else {
            Card hero;
            Card attacker = game.getBoard().
                    get(action.getCardAttacker().getX())
                    .get(action.getCardAttacker().getY());

            if (game.getPlayerTurn() == 1) {
                hero = game.getPlayerTwo().getHeroCard();
            } else {
                hero = game.getPlayerOne().getHeroCard();
            }

            hero.setHealth(hero.getHealth() - attacker.getAttackDamage());
            attacker.setAttacked(true);

            if (hero.getHealth() <= 0) {
                if (game.getPlayerTurn() == 1) {
                    GwentStoneLite.getOutputCreator().
                            gameEnded("Player one killed the enemy hero.");
                    game.getPlayerOne().
                            setGamesWon(game.getPlayerOne().getGamesWon() + 1);
                } else {
                    GwentStoneLite.getOutputCreator().
                            gameEnded("Player two killed the enemy hero.");
                    game.getPlayerTwo().
                            setGamesWon(game.getPlayerTwo().getGamesWon() + 1);
                }

                game.setGameEnded(true);
            }
        }
    }

    /**
     * Executes the ability of the card, which is specific to each card.
     * @param game the GameSession object that holds the current state of the game.
     * @param action the actionsInput object containing details
     *              about the action being performed such as the
     *               card using the ability and any targets involved.
     */
    public void useAbility(final GameSession game, final ActionsInput action) {
    }
}
