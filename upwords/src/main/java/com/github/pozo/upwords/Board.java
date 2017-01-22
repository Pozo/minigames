package com.github.pozo.upwords;

public interface Board {
    //boolean lastStepIsTheWinner();

    void put(Step step) throws IllegalCoordinateException;

    int getWidth();

    int getHeight();
}
