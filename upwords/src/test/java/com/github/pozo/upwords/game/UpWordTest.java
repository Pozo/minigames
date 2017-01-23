package com.github.pozo.upwords.game;

import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.player.DefaultPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpWordTest {
    @Test
    public void start() throws Exception {

    }

    @Test
    public void hasWinner() throws Exception {

    }

    @Test
    public void getCurrentPlayer() throws Exception {

    }

    @Test
    public void nextTurnFirstTurn() throws Exception {
        // GIVEN
        Player player1 = new DefaultPlayer("one");
        Player player2 = new DefaultPlayer("two");

        PlayerRaffler playerRaffler = mock(PlayerRaffler.class);
        when(playerRaffler.raffleFirstPlayer()).thenReturn(player1);

        UpWord upWord = new UpWord(player1, player2, playerRaffler);
        // WHEN
        upWord.start();
        // THEN
        final Player expectedCurrentPlayer = player1;
        final Player actualCurrentPlayer = upWord.getCurrentPlayer();
        assertEquals(expectedCurrentPlayer, actualCurrentPlayer);

        final Player expectedPreviousPlayer = null;
        final Player actualPreviousPlayer = upWord.getPreviousPlayer();
        assertEquals(expectedPreviousPlayer, actualPreviousPlayer);
    }

    @Test
    public void nextTurnAfterTwoTurn() throws Exception {
        // GIVEN
        Player player1 = new DefaultPlayer("one");
        Player player2 = new DefaultPlayer("two");

        PlayerRaffler playerRaffler = mock(PlayerRaffler.class);
        when(playerRaffler.raffleFirstPlayer()).thenReturn(player1);

        UpWord upWord = new UpWord(player1, player2, playerRaffler);
        // WHEN
        upWord.start();
        upWord.nextTurn();
        // THEN
        final Player expectedCurrentPlayer = player2;
        final Player actualCurrentPlayer = upWord.getCurrentPlayer();
        assertEquals(expectedCurrentPlayer, actualCurrentPlayer);

        final Player expectedPreviousPlayer = player1;
        final Player actualPreviousPlayer = upWord.getPreviousPlayer();
        assertEquals(expectedPreviousPlayer, actualPreviousPlayer);
    }

    @Test
    public void nextTurnAfterThreeTurn() throws Exception {
        // GIVEN
        Player player1 = new DefaultPlayer("one");
        Player player2 = new DefaultPlayer("two");

        PlayerRaffler playerRaffler = mock(PlayerRaffler.class);
        when(playerRaffler.raffleFirstPlayer()).thenReturn(player1);

        UpWord upWord = new UpWord(player1, player2, playerRaffler);
        // WHEN
        upWord.start();
        upWord.nextTurn();
        upWord.nextTurn();
        // THEN
        final Player expectedCurrentPlayer = player1;
        final Player actualCurrentPlayer = upWord.getCurrentPlayer();
        assertEquals(expectedCurrentPlayer, actualCurrentPlayer);

        final Player expectedPreviousPlayer = player2;
        final Player actualPreviousPlayer = upWord.getPreviousPlayer();
        assertEquals(expectedPreviousPlayer, actualPreviousPlayer);
    }

    @Test
    public void put() throws Exception {

    }

    @Test
    public void pass() throws Exception {

    }

    @Test
    public void replace() throws Exception {

    }

    @Test
    public void finishTurn() throws Exception {

    }

    @Test
    public void addGameEventListener() throws Exception {

    }

    @Test
    public void removeGameEventListener() throws Exception {

    }

}