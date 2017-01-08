package com.github.pozo.tictactoe;

import com.github.pozo.tictactoe.board.Marks;

public interface Board {
    boolean lastStepIsTheWinner();

    void mark(Coordinate coordinate, Marks mark) throws IllegalCoordinateException;

    int getWidth();

    int getHeight();
}
