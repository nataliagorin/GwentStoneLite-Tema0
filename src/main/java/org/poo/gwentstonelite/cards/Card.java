package org.poo.gwentstonelite.cards;

import org.poo.fileio.CardInput;

import java.util.ArrayList;

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

    public void setMana(final int mana) {
            this.mana = mana;
        }

    public final int getAttackDamage() {
            return attackDamage;
        }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final int getHealth() {
            return health;
        }

    public void setHealth(final int health) {
            this.health = health;
        }

    public final String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
            this.description = description;
    }

    public final ArrayList<String> getColors() {
            return colors;
        }

    public void setColors(final ArrayList<String> colors) {
            this.colors = colors;
        }

    public final String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    public final boolean hasAttacked() {
        return attacked;
    }

    public void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }

    }
