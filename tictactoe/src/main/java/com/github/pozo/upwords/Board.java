package com.github.pozo.upwords;

import com.github.pozo.upwords.board.Marks;

public interface Board {
    boolean lastStepIsTheWinner();

    void mark(Coordinate coordinate, Marks mark) throws IllegalCoordinateException;

    int getWidth();

    int getHeight();
}
