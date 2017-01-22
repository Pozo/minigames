package com.github.pozo.upwords;

public interface GameEventListener {
    void gameStarted(Player firstPlayer);

    void nextTurn(Player player);

    void gameEnded();
}
