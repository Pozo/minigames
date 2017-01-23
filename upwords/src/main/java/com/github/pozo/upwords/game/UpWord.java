package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.GameEventListener;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.PlayerListener;
import com.github.pozo.upwords.Step;
import com.github.pozo.upwords.board.UpWordsBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class UpWord implements PlayerListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpWord.class);

    private final Map<Player, List<String>> gameState;

    private final Player playerOne;
    private final Player playerTwo;

    private final Board gameBoard;
    private final CharacterMixer characterMixer;
    private PlayerRaffler playerRaffler;

    private final GameEventProducer gameEventProducer;

    private Player firstPlayer;
    private Player currentPlayer;
    private Player previousPlayer;
    private List<Step> previousSteps;

    private boolean hasWinner;
    private LinkedBlockingDeque<GameEventListener> gameEventListeners;

    public UpWord(Player playerOne, Player playerTwo) {
        this(playerOne, playerTwo, new PlayerRaffler(playerOne, playerTwo));
    }

    UpWord(Player playerOne, Player playerTwo, PlayerRaffler playerRaffler) {
        checkParams(playerOne, playerTwo);
        this.gameState = new HashMap<>();

        this.gameBoard = new UpWordsBoard();
        this.characterMixer = new CharacterMixer();
        this.playerRaffler = playerRaffler;

        this.previousSteps = new ArrayList<>();
        this.gameEventListeners = new LinkedBlockingDeque<>();

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.gameEventProducer = new GameEventProducer(gameBoard, playerOne, playerTwo);

        playerOne.addPlayerListener(this);
        playerTwo.addPlayerListener(this);
    }

    private void checkParams(Player playerOne, Player playerTwo) {
        if (playerOne == null) {
            throw new NullPointerException("playerOne cant be null");
        }
        if (playerTwo == null) {
            throw new NullPointerException("playerTwo cant be null");
        }
    }

    public void start() {
        firstPlayer = playerRaffler.raffleFirstPlayer();
        currentPlayer = firstPlayer;

        List<String> initialCharactersOne = characterMixer.raffleCharacters();
        List<String> initialCharactersTwo = characterMixer.raffleCharacters();

        gameState.put(playerOne, initialCharactersOne);
        gameState.put(playerTwo, initialCharactersTwo);

        playerOne.gameStarted(new ArrayList<>(initialCharactersOne), 10, 10);
        playerTwo.gameStarted(new ArrayList<>(initialCharactersTwo), 10, 10);

        LOGGER.info("first player is " + firstPlayer);

        for (GameEventListener gameEventListener : gameEventListeners) {
            gameEventListener.gameStarted(firstPlayer);
        }
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    Player getPreviousPlayer() {
        return this.previousPlayer;
    }

    public void nextTurn() {
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

        } else if (currentPlayer.equals(playerOne)) {
            this.currentPlayer = playerTwo;

        } else if (currentPlayer.equals(playerTwo)) {
            this.currentPlayer = playerOne;

        }
    }

    private void setupPreviousPlayer() {
        this.previousPlayer = currentPlayer;
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
            LOGGER.info(player.getName() + " puts " + character + " to " + step);
            gameBoard.put(step);
            previousSteps.add(step);
            currentPlayerCharacters.remove(character);
        } else {
            throw new IllegalArgumentException(player.getName() + " dont have this character:" + character);
        }
    }

    @Override
    public void pass(Player player) {
        LOGGER.info(player.getName() + " passed");
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
