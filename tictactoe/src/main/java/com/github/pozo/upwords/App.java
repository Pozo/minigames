package com.github.pozo.upwords;

import com.github.pozo.upwords.game.TicTacToe;
import com.github.pozo.upwords.player.DefaultPlayer;

public class App {
    public static void main(String[] args) {
        final DefaultPlayer playerOne = new DefaultPlayer("Bela");
        final DefaultPlayer playerTwo = new DefaultPlayer("Otto");

        TicTacToe ticTacToe = new TicTacToe(playerOne, playerTwo);
        ticTacToe.start();

        while (!ticTacToe.hasWinner()) {
            ticTacToe.iterate();
        }

    }
}
