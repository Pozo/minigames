package com.github.pozo.upwords.player;

import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.Step;
import com.github.pozo.upwords.game.CharacterMixer;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayer implements Player {
    private String name;
    private PlayerListener playerListener;
    private List<Coordinate> opponentSteps;

    private int boardHeight;
    private int boardWidth;
    private List<String> yourCharacters;

    public DefaultPlayer(String name) {
        this.name = name;
        this.opponentSteps = new ArrayList<>();
        this.yourCharacters = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void put(Step step) throws IllegalCoordinateException {
        final String subjectCharacter = step.getCharacter();

        if (yourCharacters.contains(subjectCharacter)) {
            playerListener.put(this, step);
            yourCharacters.remove(subjectCharacter);
        } else {
            throw new IllegalArgumentException("You dont have this character :" + subjectCharacter);
        }
    }

    @Override
    public void pass() {
        playerListener.pass(this);
    }

    @Override
    public void replace(String character) {
        if (yourCharacters.contains(character)) {
            playerListener.replace(this, character);
            yourCharacters.remove(character);
        } else {
            throw new IllegalArgumentException("You dont have this character :" + character);
        }
    }

    @Override
    public void finishTurn() {
        playerListener.finishTurn(this);
    }

    @Override
    public List<String> getCharacters() {
        return yourCharacters;
    }

    @Override
    public void gameStarted(List<String> yourCharacters, int boardHeight, int boardWidth) {
        this.yourCharacters = yourCharacters;
    }

    @Override
    public void drawnCharacters(List<String> newCharacters) {
        int totalSize = this.yourCharacters.size() + newCharacters.size();

        if (totalSize > CharacterMixer.MAX_CHARACTER_NUMBER) {
            throw new IllegalArgumentException("You cant hold more than " + CharacterMixer.MAX_CHARACTER_NUMBER + " character");
        }
        this.yourCharacters.addAll(newCharacters);
    }

    @Override
    public void yourTurn(List<Step> steps) {
        if (steps != null) {
            // I'm the first
        }
        boolean success = false;
        try {
            while (!success) {
                final String myFirstCharacter = yourCharacters.get(0);
                final Coordinate myFirstPosition = new Coordinate(0, 0);

                final Step firstStep = new Step(myFirstPosition, myFirstCharacter);

                playerListener.put(this, firstStep);
                success = true;
            }
        } catch (IllegalCoordinateException exception) {
            exception.printStackTrace();
        }
        playerListener.finishTurn(this);
    }

    @Override
    public void youWin() {
        // TODO implement
    }

    @Override
    public void youLoose() {
        // TODO implement
    }

    public void addPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    @Override
    public String toString() {
        return "DefaultPlayer{" +
                "name='" + name + '\'' +
                ", yourCharacters=" + yourCharacters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPlayer that = (DefaultPlayer) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
