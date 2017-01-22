package com.github.pozo.upwords;

public interface PlayerListener {
    void check(Player player, Coordinate coordinate) throws IllegalCoordinateException;

    void abandon(Player player);
}
