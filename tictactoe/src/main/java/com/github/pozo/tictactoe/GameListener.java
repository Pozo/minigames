package com.github.pozo.tictactoe;

import com.github.pozo.tictactoe.board.Marks;

interface GameListener {
    void gameStarted(Marks yourMark, int boardHeight, int boardWidth);

    void yourTurn(Coordinate opponentPlayerStep);

    void youWin();

    void youLoose();
}
