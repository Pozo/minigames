package com.github.pozo.upwords;

import com.github.pozo.upwords.board.Marks;

public interface Player extends GameListener {
    String getName();

    void check(Coordinate coordinate) throws IllegalCoordinateException;

    Marks getMark();
}
