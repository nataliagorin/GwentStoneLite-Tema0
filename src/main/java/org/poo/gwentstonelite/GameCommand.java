package org.poo.gwentstonelite;

import org.poo.fileio.ActionsInput;
import org.poo.gwentstonelite.cards.Card;

public final class GameCommand {
    private GameSession game;
    public GameCommand(final GameSession game) {
        this.game = game;
    }

    public void performAction(final ActionsInput action) {
        switch (action.getCommand()) {
            // Debugging commands
            case "getCardsInHand" -> getCardsInHand(action);
            case "getPlayerDeck" -> getPlayerDeck(action);
            case "getCardsOnTable" -> getCardsOnTable(action);
            case "getPlayerTurn" -> getPlayerTurn(action);
            case "getPlayerHero" -> getPlayerHero(action);
            case "getCardAtPosition" -> getCardAtPosition(action);
            case "getPlayerMana" -> getPlayerMana(action);
            case "getFrozenCardsOnTable" -> getFrozenCardsOnTable(action);

            // Statistics commands
            case "getTotalGamesPlayed" -> getTotalGamesPlayed(action);
            case "getPlayerOneWins", "getPlayerTwoWins" -> getPlayerWins(action);

            // Cards commands
            case "placeCard" -> placeCard(action);
            case "cardUsesAttack" -> cardUsesAttack(action);
            case "cardUsesAbility" -> cardUsesAbility(action);
            case "useAttackHero" -> useAttackHero(action);
            case "useHeroAbility" -> useHeroAbility(action);

            // End Turn command
            case "endPlayerTurn" -> endPlayerTurn(action);

            default -> throw new IllegalArgumentException("Invalid command.");
        }
    }

    private void getFrozenCardsOnTable(ActionsInput action) {
    }

    public void endPlayerTurn(final ActionsInput action) {
        if (game.getPlayerTurn() == 1) {
            game.setPlayerOneEndTurn(true);
            GwentStoneLite.getCardActions().
                    resetCardsFromBoard(game.getBoard(), GwentStoneLite.ROW2, GwentStoneLite.ROW3);
            GwentStoneLite.getCardActions().resetHero(game.getPlayerOne());
            game.setPlayerTurn(2);
        } else {
            game.setPlayerTwoEndTurn(true);
            GwentStoneLite.getCardActions().
                    resetCardsFromBoard(game.getBoard(), GwentStoneLite.ROW0, GwentStoneLite.ROW1);
            GwentStoneLite.getCardActions().resetHero(game.getPlayerTwo());
            game.setPlayerTurn(1);
        }
    }

    private void useHeroAbility(ActionsInput action) {
    }

    private void useAttackHero(ActionsInput action) {
    }

    private void cardUsesAbility(ActionsInput action) {
    }

    private void cardUsesAttack(ActionsInput action) {
    }

    public void placeCard(final ActionsInput action) {
        Player player;
        Card card;
        String errorMessage = "null";
        int row;

        if (game.getPlayerTurn() == 1) {
            player = game.getPlayerOne();
        } else {
            player = game.getPlayerTwo();
        }

        card = player.getHandCards().get(action.getHandIdx());


        if (player.getMana() < card.getMana()) {
            errorMessage = "Not enough mana to place card on table.";
        } else {
            row = getRow(card);

            if (game.getBoard().get(row).size() == GwentStoneLite.ROWFULL) {
                errorMessage = "Cannot place card on table since row is full.";
            } else {
                game.getBoard().get(row).add(player.getHandCards().remove(action.getHandIdx()));
                player.setMana(player.getMana() - card.getMana());
            }
        }

        if (!errorMessage.equals("null")) {
            GwentStoneLite.getOutputCreator().createPlaceCardError(errorMessage, action);
        }
    }

    public int getRow(final Card card) {
        if (game.getPlayerTurn() == 1) {
            if (GwentStoneLite.getCardActions().checkRow(card).equals("front")) {
                return GwentStoneLite.ROW2;
            } else {
                return GwentStoneLite.ROW3;
            }
        } else {
            if (GwentStoneLite.getCardActions().checkRow(card).equals("front")) {
                return GwentStoneLite.ROW1;
            } else {
                return GwentStoneLite.ROW0;
            }
        }
    }

    private void getPlayerWins(ActionsInput action) {
    }

    private void getTotalGamesPlayed(ActionsInput action) {
    }

    private void getPlayerMana(ActionsInput action) {
    }

    private void getCardAtPosition(ActionsInput action) {
    }

    public void getPlayerHero(final ActionsInput action) {
        if (action.getPlayerIdx() == 1) {
            GwentStoneLite.getOutputCreator().
                    playerHeroOutput(game.getPlayerOne().getHeroCard(), action);
        } else {
            GwentStoneLite.getOutputCreator().
                    playerHeroOutput(game.getPlayerTwo().getHeroCard(), action);
        }
    }

    public void getPlayerTurn(final ActionsInput action) {
        GwentStoneLite.getOutputCreator().playerTurnOutput(game.getPlayerTurn(), action);
    }

    private void getCardsOnTable(ActionsInput action) {
    }

    private void getCardsInHand(ActionsInput action) {
    }

    public void getPlayerDeck(final ActionsInput action) {
        if (action.getPlayerIdx() == 1) {
            GwentStoneLite.getOutputCreator().
                    cardsOutput(game.getPlayerOne().getCardsToPlay(), action);
        } else {
            GwentStoneLite.getOutputCreator().
                    cardsOutput(game.getPlayerTwo().getCardsToPlay(), action);
        }
    }
}
