package com.github.pozo.upwords;

import java.util.List;

interface GameListener {
    void gameStarted(List<Character> yourCharacters, int boardHeight, int boardWidth);

    void yourTurn(List<Step> steps);

    void youWin();

    void youLoose();
}
