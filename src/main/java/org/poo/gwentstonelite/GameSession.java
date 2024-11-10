package org.poo.gwentstonelite;

import lombok.Getter;
import lombok.Setter;
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
    @Getter
    private final Player playerOne;
    @Getter
    private final Player playerTwo;
    @Getter
    private final ArrayList<ArrayList<Card>> board;
    private final StartGameInput setupGame;
    @Setter
    @Getter
    private ArrayList<ActionsInput> actions;
    @Setter
    @Getter
    private int playerTurn;
    private int manaPerRound;
    @Setter
    private boolean playerOneEndTurn;
    @Setter
    private boolean playerTwoEndTurn;
    @Setter
    @Getter
    private boolean gameEnded;
    private final GameCommand actionPerformer;

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

}
