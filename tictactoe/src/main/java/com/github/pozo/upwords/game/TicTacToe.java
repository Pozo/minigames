package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.board.TicTacToeBoard;
import com.github.pozo.upwords.player.DefaultPlayer;

import java.util.Random;

public class TicTacToe implements PlayerListener {
    private final Player playerOne;
    private final Player playerTwo;

    private final Board gameTicTacToeBoard;
    private final GameEventProducer gameEventProducer;

    private Player previousPlayer;
    private Coordinate previousCoordinate;

    private boolean hasWinner;

    public TicTacToe(DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        checkParams(playerOne, playerTwo);
        gameTicTacToeBoard = new TicTacToeBoard();

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        gameEventProducer = new GameEventProducer(gameTicTacToeBoard, playerOne, playerTwo);

        playerOne.addPlayerListener(this);
        playerTwo.addPlayerListener(this);
    }

    private void checkParams(DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        if (playerOne == null) {
            throw new NullPointerException("playerOne cant be null");
        }
        if (playerTwo == null) {
            throw new NullPointerException("playerTwo cant be null");
        }
    }

    public void start() {
        Player firstPlayer = raffleFirstPlayer();

        System.out.println("first player is " + firstPlayer);

        gameEventProducer.fireGameStartEvent(firstPlayer);
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public void iterate() {
        gameEventProducer.fireNextPlayerEvent(previousPlayer, previousCoordinate);
    }

    private Player raffleFirstPlayer() {
        Random rand = new Random();

        int randomInt = rand.nextInt(100) + 1;
        if (randomInt % 2 == 0) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    @Override
    public void check(Player playerWhoChecked, Coordinate coordinate) throws IllegalCoordinateException {
        System.out.println(playerWhoChecked + " marked with " + playerWhoChecked.getMark() + " to " + coordinate);
        gameTicTacToeBoard.mark(coordinate, playerWhoChecked.getMark());

        previousCoordinate = coordinate;

        if (gameTicTacToeBoard.lastStepIsTheWinner()) {
            gameEventProducer.fireGameEndEvent(playerWhoChecked);
            this.hasWinner = true;
        }

        this.previousPlayer = playerWhoChecked;
    }

    @Override
    public void abandon(Player playerWhoGaveUp) {
        gameEventProducer.fireGameEndEventWithAbandon(playerWhoGaveUp);
        hasWinner = true;
    }
}
