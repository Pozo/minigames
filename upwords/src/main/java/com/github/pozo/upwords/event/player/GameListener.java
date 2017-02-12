package com.github.pozo.upwords.event.player;

import com.github.pozo.upwords.Step;

import java.util.List;

public interface GameListener {
    void gameStarted(List<String> yourCharacters, int boardHeight, int boardWidth);

    void drawnCharacters(List<String> yourCharacters);

    void yourTurn(List<Step> steps);

    void youWin();

    void youLoose();
}
