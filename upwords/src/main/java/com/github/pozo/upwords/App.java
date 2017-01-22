package com.github.pozo.upwords;

import com.github.pozo.upwords.game.UpWord;
import com.github.pozo.upwords.player.DefaultPlayer;

public class App {
    public static void main(String[] args) throws IllegalCoordinateException {
        final DefaultPlayer playerOne = new DefaultPlayer("Bela");
        final DefaultPlayer playerTwo = new DefaultPlayer("Otto");

        UpWord upWord = new UpWord(playerOne, playerTwo);

        upWord.addGameEventListener(new GameEventListener() {
            @Override
            public void gameStarted(final Player firstPlayer) {
                System.out.println("App.gameStarted");
                try {
                    String character = firstPlayer.getCharacters().get(0);
                    Step step = new Step(new Coordinate(0, 0), character);
                    firstPlayer.put(step);
                } catch (IllegalCoordinateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void nextTurn(Player player) {
                System.out.println("App.nextTurn");
            }

            @Override
            public void gameEnded() {
                System.out.println("App.gameEnded");
            }
        });
        upWord.start();

//        while (!upWord.hasWinner()) {
//            upWord.iterate();
//        }
    }
}
