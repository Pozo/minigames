package com.github.pozo.upwords;

import com.github.pozo.upwords.board.Marks;

interface GameListener {
    void gameStarted(Marks yourMark, int boardHeight, int boardWidth);

    void yourTurn(Coordinate opponentPlayerStep);

    void youWin();

    void youLoose();
}
