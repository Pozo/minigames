package com.github.pozo.upwords.board;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;

import java.util.ArrayList;

public class TicTacToeBoard implements Board {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;

    private final int width;
    private final int height;

    private final Marks[][] board;

    private Coordinate lastCoordinate;
    private Marks lastMark;

    public TicTacToeBoard() {
        this(BOARD_WIDTH, BOARD_HEIGHT);
    }

    private final int WINNER_SEQUENCE_LENGTH = 3;

    public TicTacToeBoard(int width, int height) {
        this.width = width;
        this.height = height;

        this.board = new Marks[height][width];
    }

    public void mark(Coordinate coordinate, Marks mark) throws IllegalCoordinateException {
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (x < 0 || y < 0) {
            throw new IllegalCoordinateException("Coordinate must contains between zero and X");
        }
        if (x >= width || y >= height) {
            throw new IllegalCoordinateException("Coordinate cant be bigger than the boardsize");
        }
        if (board[y][x] != null) {
            throw new IllegalCoordinateException("There is already a mark at " + coordinate);
        } else {
            board[y][x] = mark;
            this.lastCoordinate = coordinate;
            this.lastMark = mark;
        }
    }

    public boolean lastStepIsTheWinner() {
        int x = lastCoordinate.getX();
        int y = lastCoordinate.getY();

        final Marks[] row = board[y];

        ArrayList<Marks> markBuffer = new ArrayList<Marks>();
        for (int i = 0; i < row.length; i++) {
            final Marks mark = row[i];
            if (mark != null && mark.equals(lastMark)) {
                markBuffer.add(mark);

                if (markBuffer.size() == WINNER_SEQUENCE_LENGTH) {
                    return true;
                }
            } else {
                markBuffer.clear();
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Marks[] rows : board) {
            for (Marks column : rows) {
                if (column == null) {
                    builder.append(" - ");
                } else if (column.equals(Marks.CIRCLE)) {
                    builder.append(" O ");
                } else if (column.equals(Marks.CROSS)) {
                    builder.append(" X ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
