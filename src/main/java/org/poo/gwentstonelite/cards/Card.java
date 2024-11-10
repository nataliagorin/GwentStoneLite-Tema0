package org.poo.gwentstonelite.cards;

import lombok.Setter;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.gwentstonelite.GameSession;
import org.poo.gwentstonelite.GwentStoneLite;

import java.util.ArrayList;

@Setter
public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
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

    public final int getMana() {
        return mana;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final int getHealth() {
        return health;
    }

    public final String getDescription() {
        return description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final String getName() {
        return name;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final boolean hasAttacked() {
        return attacked;
    }

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

    public void useAbility(final GameSession game, final ActionsInput action) {
    }
}
