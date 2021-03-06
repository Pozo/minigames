package com.github.pozo.upwords;

import com.github.pozo.upwords.event.player.GameListener;

import java.util.List;

public interface Player extends GameListener {
    String getName();

    void put(Step step) throws IllegalCoordinateException;

    void pass();

    void replace(String character);

    void finishTurn();

    List<String> getCharacters();

    void addPlayerListener(PlayerListener playerListener);
}
