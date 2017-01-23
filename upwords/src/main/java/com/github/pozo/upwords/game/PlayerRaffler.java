package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Player;

import java.util.Random;

public class PlayerRaffler {

    private final Player playerOne;
    private final Player playerTwo;

    public PlayerRaffler(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    Player raffleFirstPlayer() {
        Random rand = new Random();

        int randomInt = rand.nextInt(100) + 1;
        if (randomInt % 2 == 0) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }
}