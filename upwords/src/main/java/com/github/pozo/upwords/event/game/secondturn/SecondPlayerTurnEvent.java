package com.github.pozo.upwords.event.game.secondturn;

import com.github.pozo.upwords.Player;

public class SecondPlayerTurnEvent {
    private final Player player;

    public SecondPlayerTurnEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
