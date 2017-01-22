package com.github.pozo.upwords;

public interface PlayerListener {
    void put(Player player, Step step) throws IllegalCoordinateException;

    void pass(Player player);

    void replace(Player player, String character);

    void finishTurn(Player player);
}
