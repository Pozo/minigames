package com.github.pozo.upwords.event.game.firstturn;

import com.github.pozo.upwords.Player;

public class FirstPlayerTurnEvent {
    private final Player player;

    public FirstPlayerTurnEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
