package com.github.pozo.upwords;

public interface PlayerListener {
    void put(Player defaultPlayer, Step step) throws IllegalCoordinateException;

    void pass(Player player);

    void finishTurn(Player player);
}
