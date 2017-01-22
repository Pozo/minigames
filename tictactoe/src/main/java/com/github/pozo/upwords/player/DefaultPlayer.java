package com.github.pozo.upwords.player;

import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.board.Marks;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayer implements Player {
    private String name;
    private PlayerListener playerListener;
    private Marks mark;

    private List<Coordinate> opponentSteps;

    private int boardHeight;
    private int boardWidth;

    public DefaultPlayer(String name) {
        this.name = name;
        this.opponentSteps = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void check(Coordinate coordinate) throws IllegalCoordinateException {
        playerListener.check(this, coordinate);
    }

    @Override
    public void gameStarted(Marks mark, int boardHeight, int boardWidth) {
        this.mark = mark;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }

    @Override
    public void yourTurn(Coordinate opponentPlayerStep) {

        final boolean youAreTheFirst = opponentPlayerStep == null;
        if (youAreTheFirst) {
            check(0, 0);
        } else {
            opponentSteps.add(opponentPlayerStep);
            int x = opponentPlayerStep.getX();
            int y = opponentPlayerStep.getY();

            if (y == 1) {
                check(x, 0);
            } else {
                check(x, 1);
            }
        }

    }

    private void check(int x, int y) {
        boolean success = false;

        while (!success) {
            try {
                check(new Coordinate(x, y));
                success = true;
            } catch (IllegalCoordinateException e) {
                x += 1;
                //playerListener.abandon(this);
                System.err.println(this + e.getMessage());
            }
        }
    }

    @Override
    public void youWin() {
        System.out.println(name + ": Yaaaay");

    }

    @Override
    public void youLoose() {
        System.out.println(name + ": :(");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPlayer player = (DefaultPlayer) o;

        return name.equals(player.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void addPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public Marks getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "DefaultPlayer{" +
                "name='" + name + '\'' +
                '}';
    }
}
