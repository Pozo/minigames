package com.github.pozo.upwords.player;

import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.board.Marks;

public class RESTPlayer implements Player {
    private Marks mark;

    private int boardHeight;
    private int boardWidth;
    private String name;

    public RESTPlayer(String name) {
        this.name = name;
    }


    @Override
    public void gameStarted(Marks yourMark, int boardHeight, int boardWidth) {
        this.mark = yourMark;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void yourTurn(Coordinate opponentPlayerStep) {

    }

    @Override
    public void check(Coordinate coordinate) {

    }

    @Override
    public Marks getMark() {
        return mark;
    }

    @Override
    public void youWin() {

    }

    @Override
    public void youLoose() {

    }
}
