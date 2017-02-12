package com.github.pozo.upwords.event.game.end;

import com.github.pozo.upwords.Player;

public class GameEndedEvent {
    private final Player winner;

    public GameEndedEvent(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
