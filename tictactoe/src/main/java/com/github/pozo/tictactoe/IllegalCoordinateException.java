package com.github.pozo.tictactoe;

public class IllegalCoordinateException extends Exception {
    public IllegalCoordinateException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
