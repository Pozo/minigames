package com.github.pozo.tictactoe.game;

import com.github.pozo.tictactoe.Board;
import com.github.pozo.tictactoe.Coordinate;
import com.github.pozo.tictactoe.Player;
import com.github.pozo.tictactoe.board.Marks;
import com.github.pozo.tictactoe.player.DefaultPlayer;

class GameEventProducer {
    private Board gameTicTacToeBoard;
    private final DefaultPlayer playerOne;
    private final DefaultPlayer playerTwo;

    GameEventProducer(Board gameTicTacToeBoard, DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        this.gameTicTacToeBoard = gameTicTacToeBoard;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    void fireGameStartEvent(Player firstPlayer) {
        if (firstPlayer.equals(playerOne)) {
            playerOne.gameStarted(Marks.CROSS, gameTicTacToeBoard.getHeight(), gameTicTacToeBoard.getWidth());
            playerTwo.gameStarted(Marks.CIRCLE, gameTicTacToeBoard.getHeight(), gameTicTacToeBoard.getWidth());
        } else {
            playerOne.gameStarted(Marks.CIRCLE, gameTicTacToeBoard.getHeight(), gameTicTacToeBoard.getWidth());
            playerTwo.gameStarted(Marks.CROSS, gameTicTacToeBoard.getHeight(), gameTicTacToeBoard.getWidth());
        }
        firstPlayer.yourTurn(null);
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

    void fireNextPlayerEvent(Player previousPlayer, Coordinate previousCoordinate) {
        if (previousPlayer.equals(playerOne)) {
            playerTwo.yourTurn(previousCoordinate);
        } else {
            playerOne.yourTurn(previousCoordinate);
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
