package org.poo.gwentstonelite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstonelite.cards.Card;

import java.util.ArrayList;

public final class OutputBuilder {
    private final ArrayNode output;
    private final ObjectMapper objMapper;

    public OutputBuilder(final ArrayNode output) {
        this.output = output;
        this.objMapper = new ObjectMapper();
    }

    public void cardsOutput(final ArrayList<Card> cards, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("playerIdx", action.getPlayerIdx());

        ArrayNode arrayNode = objMapper.createArrayNode();
        for (Card card : cards) {
            arrayNode.add(createCardObject(card));
        }
        objNode.set("output", arrayNode);

        output.add(objNode);
    }

    public ObjectNode createCardObject(final Card card) {
        ObjectNode cardObject = objMapper.createObjectNode();

        cardObject.put("mana", card.getMana());
        cardObject.put("attackDamage", card.getAttackDamage());
        cardObject.put("health", card.getHealth());
        cardsOutput(card, cardObject);

        return cardObject;
    }

    private void cardsOutput(Card card, ObjectNode cardObject) {
        cardObject.put("description", card.getDescription());

        ArrayNode colors = objMapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }
        cardObject.set("colors", colors);

        cardObject.put("name", card.getName());
    }

    public void playerHeroOutput(final Card heroCard, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("playerIdx", action.getPlayerIdx());
        objNode.set("output", createHeroObject(heroCard));

        output.add(objNode);
    }

    public ObjectNode createHeroObject(final Card heroCard) {
        ObjectNode heroObject = objMapper.createObjectNode();

        heroObject.put("mana", heroCard.getMana());
        cardsOutput(heroCard, heroObject);
        heroObject.put("health", heroCard.getHealth());

        return heroObject;
    }

    public void playerTurnOutput(final int playerTurn, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("output", playerTurn);

        output.add(objNode);
    }

    public void createPlaceCardError(final String errorMessage, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("handIdx", action.getHandIdx());
        objNode.put("error", errorMessage);

        output.add(objNode);
    }

    public void gameEnded(final String message) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("gameEnded", message);

        output.add(objNode);
    }

    public void cardUsesAttackError(final String errorMessage, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());

        ObjectNode attacker = objMapper.createObjectNode();
        attacker.put("x", action.getCardAttacker().getX());
        attacker.put("y", action.getCardAttacker().getY());
        objNode.set("cardAttacker", attacker);

        if (action.getCommand().equals("cardUsesAttack")
                || action.getCommand().equals("cardUsesAbility")) {
            ObjectNode attacked = objMapper.createObjectNode();
            attacked.put("x", action.getCardAttacked().getX());
            attacked.put("y", action.getCardAttacked().getY());
            objNode.set("cardAttacked", attacked);
        }

        objNode.put("error", errorMessage);

        output.add(objNode);
    }

    public void boardOutput(final ArrayList<ArrayList<Card>> board, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());

        ArrayNode bigArray = objMapper.createArrayNode();

        for (ArrayList<Card> row : board) {
            ArrayNode arrayNode = objMapper.createArrayNode();

            for (Card card : row) {
                arrayNode.add(createCardObject(card));
            }

            bigArray.add(arrayNode);
        }
        objNode.set("output", bigArray);

        output.add(objNode);
    }

    public void useHeroAbilityError(final String errorMessage, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("affectedRow", action.getAffectedRow());
        objNode.put("error", errorMessage);

        output.add(objNode);
    }

    public void playerManaOutput(final Player player, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("playerIdx", action.getPlayerIdx());
        objNode.put("output", player.getMana());

        output.add(objNode);
    }

    public void frozenCardsOutput(final ArrayList<ArrayList<Card>> board,
                                  final ActionsInput action) {

        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());

        ArrayNode arrayNode = objMapper.createArrayNode();
        for (ArrayList<Card> row : board) {
            for (Card card : row) {
                if (card.isFrozen()) {
                    arrayNode.add(createCardObject(card));
                }
            }
        }
        objNode.set("output", arrayNode);

        output.add(objNode);
    }

    public void playerWinsOutput(final Player player, final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("output", player.getGamesWon());

        output.add(objNode);
    }

    public void totalGamesOutput(final ActionsInput action) {
        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("output", GwentStoneLite.getGamesPlayed());

        output.add(objNode);
    }

    public void cardAtPositionOutput(final Card card, final ActionsInput action,
                                     final String errorMessage) {

        ObjectNode objNode = objMapper.createObjectNode();

        objNode.put("command", action.getCommand());
        objNode.put("x", action.getX());
        objNode.put("y", action.getY());

        if (errorMessage.equals("null")) {
            objNode.set("output", createCardObject(card));
        } else {
            objNode.put("output", errorMessage);
        }

        output.add(objNode);
    }

}
