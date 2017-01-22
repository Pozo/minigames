package com.github.pozo.upwords.player;

import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.Step;

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
        playerListener.put(this, step);
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

    }

    @Override
    public void youLoose() {

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
}
