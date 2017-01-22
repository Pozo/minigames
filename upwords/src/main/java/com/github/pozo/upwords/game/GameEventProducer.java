package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.Step;
import com.github.pozo.upwords.player.DefaultPlayer;

import java.util.ArrayList;
import java.util.List;

class GameEventProducer {
    private Board gameTicTacToeBoard;
    private final DefaultPlayer playerOne;
    private final DefaultPlayer playerTwo;

    GameEventProducer(Board gameTicTacToeBoard, DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        this.gameTicTacToeBoard = gameTicTacToeBoard;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
    void fireGameEndEvent(Player winner) {
        if (winner.equals(playerOne)) {
            playerOne.youWin();
            playerTwo.youLoose();
        } else {
            playerTwo.youWin();
            playerOne.youLoose();
        }
        System.out.println(gameTicTacToeBoard);
    }

    void fireNextPlayerEvent(Player previousPlayer, List<Step> steps) {
        if (previousPlayer.equals(playerOne)) {
            playerTwo.yourTurn(steps);
        } else {
            playerOne.yourTurn(steps);
        }
    }

    public void fireGameEndEventWithAbandon(Player playerWhoGaveUp) {
        if (playerWhoGaveUp.equals(playerOne)) {
            playerTwo.youWin();
            playerOne.youLoose();
        } else {
            playerOne.youWin();
            playerTwo.youLoose();
        }
    }
}
