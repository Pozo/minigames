package com.github.pozo.tictactoe;

import com.github.pozo.tictactoe.board.Marks;

public interface Player extends GameListener {
    String getName();

    void check(Coordinate coordinate) throws IllegalCoordinateException;

    Marks getMark();
}
