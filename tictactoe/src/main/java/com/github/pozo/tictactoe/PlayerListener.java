package com.github.pozo.tictactoe;

public interface PlayerListener {
    void check(Player player, Coordinate coordinate) throws IllegalCoordinateException;

    void abandon(Player player);
}
