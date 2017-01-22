package com.github.pozo.upwords;

import com.github.pozo.upwords.game.UpWord;
import com.github.pozo.upwords.player.DefaultPlayer;

public class App {
    public static void main(String[] args) throws IllegalCoordinateException, InterruptedException {
        final DefaultPlayer playerOne = new DefaultPlayer("Bela");
        final DefaultPlayer playerTwo = new DefaultPlayer("Otto");

        UpWord upWord = new UpWord(playerOne, playerTwo);

        upWord.addGameEventListener(new GameEventListener() {
            @Override
            public void gameStarted(final Player firstPlayer) {
                System.out.println("App.gameStarted: " + firstPlayer.getName());
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
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("App.firstPlayerTurn: " + player.getName());
            }

            @Override
            public void secondPlayerTurn(Player player) {
                try {
                    String character = player.getCharacters().get(0);
                    Step step = new Step(new Coordinate(1, 1), character);
                    player.put(step);
                } catch (IllegalCoordinateException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("App.secondPlayerTurn: " + player.getName());
            }

            @Override
            public void gameEnded() {
                System.out.println("App.gameEnded");
            }
        });
        upWord.start();

        while (!upWord.hasWinner()) {
            Thread.sleep(1000);
            upWord.iterate();
        }
    }
}
