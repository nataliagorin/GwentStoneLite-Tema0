package org.poo.gwentstonelite;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.GameInput;
import org.poo.fileio.Input;

public final class GwentStoneLite {
    public static final int ROW0 = 0;
    public static final int ROW1 = 1;
    public static final int ROW2 = 2;
    public static final int ROW3 = 3;
    public static final int ROWFULL = 5;
    private Input input;
    private ArrayNode output;
    private Player playerOne;
    private Player playerTwo;
    private GameSession gameSession;
    private static int gamesPlayed;
    private static Action cardActions;
    private static OutputBuilder outputCreator;

    public GwentStoneLite(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
        this.playerOne = new Player();
        this.playerTwo = new Player();
        gamesPlayed = 0;
        cardActions = new Action();
        outputCreator = new OutputBuilder(output);
    }

    public void startGwentStoneLite() {
        playerOne.setDecks(cardActions.changeDecks(input.getPlayerOneDecks().getDecks()));
        playerTwo.setDecks(cardActions.changeDecks(input.getPlayerTwoDecks().getDecks()));
        playerOne.setGamesWon(0);
        playerTwo.setGamesWon(0);

        for (GameInput game : input.getGames()) {
            gameSession = new GameSession(playerOne, playerTwo, game);
            gameSession.prepareGame(cardActions);
            gameSession.startGame();
        }
    }
}
