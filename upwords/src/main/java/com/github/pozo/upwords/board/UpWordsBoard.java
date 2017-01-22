package com.github.pozo.upwords.board;

import com.github.pozo.upwords.Board;
import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Step;

import java.util.LinkedList;
import java.util.List;

public class UpWordsBoard implements Board {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;

    private static final int MAX_STACK_SIZE = 5;

    private final int width;
    private final int height;

    private final List<String>[][] board;

    private Coordinate lastCoordinate;
    private String lastCharacter;

    public UpWordsBoard() {
        this(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public UpWordsBoard(int width, int height) {
        this.width = width;
        this.height = height;

        this.board = new List[height][width];
    }

    public void put(Step step) throws IllegalCoordinateException {
        final Coordinate coordinate = step.getCoordinate();
        final String character = step.getCharacter();

        int x = coordinate.getX();
        int y = coordinate.getY();

        if (x < 0 || y < 0) {
            throw new IllegalCoordinateException("Coordinate must contains between zero and X");
        }
        if (x >= width || y >= height) {
            throw new IllegalCoordinateException("Coordinate cant be bigger than the boardsize");
        }
        List<String> characters = board[y][x];
        if (characters != null) {
            if (characters.size() == MAX_STACK_SIZE - 1) {
                throw new IllegalCoordinateException("This coordinate is full!");
            } else {
                put(coordinate, character);
            }
        } else {
            board[y][x] = new LinkedList<>();
            put(coordinate, character);
        }
    }

    private void put(Coordinate coordinate, String character) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        board[y][x].add(character);

        this.lastCoordinate = coordinate;
        this.lastCharacter = character;
    }

//    public boolean lastStepIsTheWinner() {
//        int x = lastCoordinate.getX();
//        int y = lastCoordinate.getY();
//
//        final Marks[] row = board[y];
//
//        ArrayList<Marks> markBuffer = new ArrayList<Marks>();
//        for (int i = 0; i < row.length; i++) {
//            final Marks mark = row[i];
//            if (mark != null && mark.equals(lastCharacter)) {
//                markBuffer.add(mark);
//
//                if (markBuffer.size() == WINNER_SEQUENCE_LENGTH) {
//                    return true;
//                }
//            } else {
//                markBuffer.clear();
//            }
//        }
//
//        return false;
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<String>[] rows : board) {
            for (List<String> column : rows) {
                if (column == null) {
                    builder.append(" ");
                } else {
                    builder.append(column);
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
