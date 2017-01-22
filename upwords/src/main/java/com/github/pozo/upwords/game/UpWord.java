package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.GameEventListener;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.Step;
import com.github.pozo.upwords.board.UpWordsBoard;
import com.github.pozo.upwords.player.DefaultPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class UpWord implements PlayerListener {
    private static final int MAX_CHARACTER_NUMBER = 7;

    private final Map<Character, Integer> possibleCharacters;
    private final Map<Player, List<Character>> gameState;

    private final Player playerOne;
    private final Player playerTwo;

    private final Board upwordBoard;
    private final GameEventProducer gameEventProducer;

    private Player firstPlayer;
    private Player previousPlayer;
    private List<Step> previousSteps;

    private boolean hasWinner;
    private LinkedBlockingDeque<GameEventListener> gameEventListeners;

    public UpWord(DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        checkParams(playerOne, playerTwo);
        upwordBoard = new UpWordsBoard();
        gameState = new HashMap<>();
        possibleCharacters = new HashMap<>();
        previousSteps = new ArrayList<>();
        gameEventListeners = new LinkedBlockingDeque<>();

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        gameEventProducer = new GameEventProducer(upwordBoard, playerOne, playerTwo);

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
        firstPlayer = raffleFirstPlayer();

        List<Character> initialCharactersOne = raffleCharactersFor(playerOne, MAX_CHARACTER_NUMBER);
        List<Character> initialCharactersTwo = raffleCharactersFor(playerTwo, MAX_CHARACTER_NUMBER);

        gameState.put(playerOne, initialCharactersOne);
        gameState.put(playerTwo, initialCharactersTwo);

        System.out.println("first player is " + firstPlayer);

        playerOne.gameStarted(new ArrayList<>(initialCharactersOne), 10, 10);
        playerTwo.gameStarted(new ArrayList<>(initialCharactersTwo), 10, 10);

        for (GameEventListener gameEventListener : gameEventListeners) {
            gameEventListener.gameStarted(firstPlayer);
        }
    }

    private List<Character> raffleCharactersFor(Player player, int characterNumber) {

        final ArrayList<Character> ruffledCharacters = new ArrayList<>('ő');
        ruffledCharacters.add('ő');
        return ruffledCharacters;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public void iterate() {
        final List<Step> VERY_FIRST_TURN = null;

        if (previousPlayer == null) {
            firstPlayer.yourTurn(VERY_FIRST_TURN);
        }

        gameEventProducer.fireNextPlayerEvent(previousPlayer, previousSteps);
        previousSteps.clear();
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
    public void put(Player player, Step step) throws IllegalCoordinateException {
        boolean isSamePlayer = previousPlayer != null && previousPlayer.equals(player);
        boolean firstPlayerRequiredButNotGet = previousPlayer == null && !firstPlayer.equals(player);
        if (isSamePlayer || firstPlayerRequiredButNotGet) {
            throw new IllegalArgumentException("This is not your turn!");
        }
        upwordBoard.put(step);
        previousSteps.add(step);
    }

    @Override
    public void pass(Player playerWhoGaveUp) {
        gameEventProducer.fireGameEndEventWithAbandon(playerWhoGaveUp);
        hasWinner = true;
    }

    @Override
    public void finishTurn(Player player) {
        this.previousPlayer = player;

        for (GameEventListener gameEventListener : gameEventListeners) {
            if (previousPlayer.equals(playerOne)) {
                gameEventListener.nextTurn(playerTwo);
            } else {
                gameEventListener.nextTurn(playerOne);
            }
        }
    }

    public void addGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListeners.add(gameEventListener);
    }

    public void removeGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListeners.remove(gameEventListener);
    }
}
