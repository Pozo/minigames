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
    private final Map<Player, List<String>> gameState;

    private final Player playerOne;
    private final Player playerTwo;

    private final Board upwordBoard;
    private final CharacterMixer characterMixer;
    private final GameEventProducer gameEventProducer;

    private Player firstPlayer;
    private Player currentPlayer;
    private Player previousPlayer;
    private List<Step> previousSteps;

    private boolean hasWinner;
    private LinkedBlockingDeque<GameEventListener> gameEventListeners;

    public UpWord(DefaultPlayer playerOne, DefaultPlayer playerTwo) {
        checkParams(playerOne, playerTwo);
        upwordBoard = new UpWordsBoard();
        characterMixer = new CharacterMixer();
        gameState = new HashMap<>();

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
        currentPlayer = firstPlayer;

        List<String> initialCharactersOne = characterMixer.raffleCharacters();
        List<String> initialCharactersTwo = characterMixer.raffleCharacters();

        gameState.put(playerOne, initialCharactersOne);
        gameState.put(playerTwo, initialCharactersTwo);

        playerOne.gameStarted(new ArrayList<>(initialCharactersOne), 10, 10);
        playerTwo.gameStarted(new ArrayList<>(initialCharactersTwo), 10, 10);

        System.out.println("first player is " + firstPlayer);

        for (GameEventListener gameEventListener : gameEventListeners) {
            gameEventListener.gameStarted(firstPlayer);
        }
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void iterate() {
        finishTurn(this.currentPlayer);

        setupPreviousPlayer();
        setupCurrentPlayer();
    }

    private void setupCurrentPlayer() {
        final List<Step> VERY_FIRST_TURN = null;
        final boolean firstTurn = previousPlayer == VERY_FIRST_TURN;

        if (firstTurn) {
            if (currentPlayer.equals(playerOne)) {
                this.currentPlayer = playerTwo;
            } else {
                this.currentPlayer = playerOne;
            }

        } else if (previousPlayer.equals(playerOne)) {
            this.currentPlayer = playerTwo;

        } else if (previousPlayer.equals(playerTwo)) {
            this.currentPlayer = playerOne;

        }
    }

    private void setupPreviousPlayer() {
        this.previousPlayer = currentPlayer;
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
        boolean isSamePlayer = previousPlayer != null && currentPlayer.equals(player);
        boolean firstPlayerRequiredButNotGet = currentPlayer == null && !firstPlayer.equals(player);

        if (isSamePlayer || firstPlayerRequiredButNotGet) {
            throw new IllegalArgumentException("This is not your turn!");
        }
        final List<String> currentPlayerCharacters = gameState.get(player);
        final String character = step.getCharacter();

        if (currentPlayerCharacters.contains(character)) {
            upwordBoard.put(step);
            previousSteps.add(step);
            currentPlayerCharacters.remove(character);
        } else {
            throw new IllegalArgumentException(player.getName() + " dont have this character:" + character);
        }
    }

    @Override
    public void pass(Player playerWhoGaveUp) {
        gameEventProducer.fireGameEndEventWithAbandon(playerWhoGaveUp);
        hasWinner = true;
    }

    @Override
    public void replace(Player player, String character) {
        final List<String> playerCharacters = gameState.get(player);

        if (playerCharacters.contains(character)) {
            String newCharacter = characterMixer.replace(character);
            playerCharacters.remove(character);
            playerCharacters.add(newCharacter);
        } else {
            throw new IllegalArgumentException(player.getName() + " dont have this character:" + character);
        }
    }

    @Override
    public void finishTurn(Player player) {
        for (GameEventListener gameEventListener : gameEventListeners) {
            if (player.equals(playerTwo)) {
                if (player.equals(firstPlayer)) {
                    // player is playerTwo and he is the FIRST player
                    gameEventListener.secondPlayerTurn(playerOne);
                } else {
                    // player is playerTwo and he is the SECOND player
                    gameEventListener.firstPlayerTurn(playerOne);
                }
            } else {
                if (player.equals(firstPlayer)) {
                    // player is playerOne and he is the FIRST player
                    gameEventListener.secondPlayerTurn(playerTwo);
                } else {
                    // player is playerOne and he is the SECOND player
                    gameEventListener.firstPlayerTurn(playerTwo);
                }
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
