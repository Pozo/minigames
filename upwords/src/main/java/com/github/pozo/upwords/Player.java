package com.github.pozo.upwords;

import java.util.List;

public interface Player extends GameListener {
    String getName();

    void put(Step step) throws IllegalCoordinateException;

    void finishTurn();

    List<String> getCharacters();
}
