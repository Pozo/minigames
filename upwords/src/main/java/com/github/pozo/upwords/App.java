package com.github.pozo.upwords;

import com.github.pozo.upwords.event.game.end.GameEndedEvent;
import com.github.pozo.upwords.event.game.end.GameEndedEventListener;
import com.github.pozo.upwords.event.game.firstturn.FirstPlayerTurnEvent;
import com.github.pozo.upwords.event.game.firstturn.FirstTurnEventEventListener;
import com.github.pozo.upwords.event.game.secondturn.SecondPlayerTurnEvent;
import com.github.pozo.upwords.event.game.secondturn.SecondTurnEventEventListener;
import com.github.pozo.upwords.event.game.start.GameStartedEvent;
import com.github.pozo.upwords.event.game.start.GameStartedEventListener;
import com.github.pozo.upwords.game.UpWord;
import com.github.pozo.upwords.player.DefaultPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IllegalCoordinateException, InterruptedException {
        final DefaultPlayer playerOne = new DefaultPlayer("Bela");
        final DefaultPlayer playerTwo = new DefaultPlayer("Otto");

        UpWord upWord = new UpWord(playerOne, playerTwo);

        upWord.addGameStartedEventListener(new GameStartedEventListener() {
            @Override
            public void gameStarted(GameStartedEvent gameStartedEvent) {
                final Player firstPlayer = gameStartedEvent.getFirstPlayer();
                LOGGER.info("App.gameStarted: " + firstPlayer.getName());
                try {
                    String character = firstPlayer.getCharacters().get(0);
                    Step step = new Step(new Coordinate(0, 0), character);
                    firstPlayer.put(step);
                } catch (IllegalCoordinateException e) {
                    e.printStackTrace();
                }
            }
        });
        upWord.addFirstPlayerTurnEventListener(new FirstTurnEventEventListener() {
            @Override
            public void firstPlayerTurn(FirstPlayerTurnEvent firstPlayerTurnEvent) {
                try {
                    final Player player = firstPlayerTurnEvent.getPlayer();
                    LOGGER.info("App.firstPlayerTurn: " + player.getName());
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    LOGGER.info(e.getMessage());
                    //player.pass();
                }
            }
        });
        upWord.addSecondPlayerTurnEventListener(new SecondTurnEventEventListener() {
            @Override
            public void secondPlayerTurn(SecondPlayerTurnEvent secondPlayerTurnEvent) {
                try {
                    final Player player = secondPlayerTurnEvent.getPlayer();
                    LOGGER.info("App.secondPlayerTurn: " + player.getName());
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    LOGGER.info(e.getMessage());
                    //player.pass();
                }
            }
        });
        upWord.addGameEndedEventListener(new GameEndedEventListener() {
            @Override
            public void gameStarted(GameEndedEvent gameStartedEvent) {
                LOGGER.info("App.gameEnded");
            }
        });
        upWord.start();

        while (!upWord.hasWinner()) {
            Thread.sleep(1000);
            upWord.nextTurn();
        }
    }
}
