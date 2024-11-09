package org.poo.gwentstonelite;

import org.poo.fileio.ActionsInput;
import org.poo.fileio.GameInput;
import org.poo.fileio.StartGameInput;
import org.poo.gwentstonelite.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class GameSession {
    public static final int MAXHEALTH = 30;
    public static final int MAXMANA = 10;
    public static final int MAXROWS = 4;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<ArrayList<Card>> board;
    private StartGameInput setupGame;
    private ArrayList<ActionsInput> actions;
    private int playerTurn;
    private int manaPerRound;
    private boolean playerOneEndTurn;
    private boolean playerTwoEndTurn;
    private boolean gameEnded;
    private GameCommand actionPerformer;

    public GameSession(final Player playerOne, final Player playerTwo, final GameInput game) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.board = new ArrayList<>();
        for (int i = 0; i < MAXROWS; i++) {
            board.add(new ArrayList<>());
        }

        this.setupGame = game.getStartGame();
        this.actions = game.getActions();
        this.actionPerformer = new GameCommand(this);
    }


    public void prepareGame(final Action cardActions) {
        playerTurn = setupGame.getStartingPlayer();
        manaPerRound = 0;
        playerOneEndTurn = false;
        playerTwoEndTurn = false;
        gameEnded = false;

        cardActions.setHeroForPlayer(playerOne, setupGame.getPlayerOneHero());
        playerOne.getHeroCard().setHealth(MAXHEALTH);

        cardActions.setHeroForPlayer(playerTwo, setupGame.getPlayerTwoHero());
        playerTwo.getHeroCard().setHealth(MAXHEALTH);

        playerOne.setMana(0);
        playerTwo.setMana(0);

        playerOne.setCardsToPlay(shuffleCards(playerOne.getDecks().
                get(setupGame.getPlayerOneDeckIdx()), setupGame.getShuffleSeed()));
        playerTwo.setCardsToPlay(shuffleCards(playerTwo.getDecks().
                get(setupGame.getPlayerTwoDeckIdx()), setupGame.getShuffleSeed()));

        playerOne.setHandCards(new ArrayList<>());
        playerTwo.setHandCards(new ArrayList<>());
    }

    public ArrayList<Card> shuffleCards(final ArrayList<Card> deck, final int shuffleSeed) {
        ArrayList<Card> shuffledDeck = new ArrayList<>();

        for (Card card : deck) {
            GwentStoneLite.getCardActions().addCard(card, shuffledDeck);
        }

        Collections.shuffle(shuffledDeck, new Random(shuffleSeed));
        return shuffledDeck;
    }

    public void startGame() {
        GwentStoneLite.setGamesPlayed(GwentStoneLite.getGamesPlayed() + 1);
        nextRound();

        for (ActionsInput action : actions) {

            actionPerformer.performAction(action);
            if (playerOneEndTurn && playerTwoEndTurn) {
                nextRound();
            }
        }
    }

    public void nextRound() {
        if (manaPerRound < MAXMANA) {
            manaPerRound++;
        }

        playerOne.setMana(playerOne.getMana() + manaPerRound);
        playerTwo.setMana(playerTwo.getMana() + manaPerRound);

        if (!playerOne.getCardsToPlay().isEmpty()) {
            playerOne.getHandCards().add(playerOne.getCardsToPlay().removeFirst());
        }

        if (!playerTwo.getCardsToPlay().isEmpty()) {
            playerTwo.getHandCards().add(playerTwo.getCardsToPlay().removeFirst());
        }

        playerOneEndTurn = false;
        playerTwoEndTurn = false;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public ArrayList<ArrayList<Card>> getBoard() {
        return board;
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerOneEndTurn(final boolean playerOneEndTurn) {
        this.playerOneEndTurn = playerOneEndTurn;
    }

    public void setPlayerTwoEndTurn(final boolean playerTwoEndTurn) {
        this.playerTwoEndTurn = playerTwoEndTurn;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(final boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
