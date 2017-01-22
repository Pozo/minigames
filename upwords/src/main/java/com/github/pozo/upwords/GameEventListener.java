package com.github.pozo.upwords;

public interface GameEventListener {
    void gameStarted(Player firstPlayer);

    void firstPlayerTurn(Player player);

    void secondPlayerTurn(Player player);

    void gameEnded();
}
