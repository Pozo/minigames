package com.github.pozo.upwords;

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

        upWord.addGameEventListener(new GameEventListener() {
            @Override
            public void gameStarted(final Player firstPlayer) {
                LOGGER.info("App.gameStarted: " + firstPlayer.getName());
                try {
                    String character = firstPlayer.getCharacters().get(0);
                    Step step = new Step(new Coordinate(0, 0), character);
                    firstPlayer.put(step);
                } catch (IllegalCoordinateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void firstPlayerTurn(Player player) {
                try {
                    LOGGER.info("App.firstPlayerTurn: " + player.getName());
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    LOGGER.info(e.getMessage());
                }

            }

            @Override
            public void secondPlayerTurn(Player player) {
                try {
                    LOGGER.info("App.secondPlayerTurn: " + player.getName());
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    LOGGER.info(e.getMessage());
                }

            }

            @Override
            public void gameEnded() {
                LOGGER.info("App.gameEnded");
            }
        });
        upWord.start();

        while (!upWord.hasWinner()) {
            Thread.sleep(1000);
            upWord.iterate();
        }
    }
}
