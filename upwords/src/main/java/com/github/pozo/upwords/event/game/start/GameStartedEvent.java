package com.github.pozo.upwords.event.game.start;

import com.github.pozo.upwords.Player;

public class GameStartedEvent {
    private final Player firstPlayer;

    public GameStartedEvent(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }
}
