package com.chess.engine.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0); // the first column in board is true, and all the remain is false
    public static final boolean[] SECOND_COLUMN = initColumn(1); // the second column in board is true, and all the remain is false
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);
    public static final int NUM_TILES_PER_ROW = 0;
    public static final int NUM_TILES = 64;
    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    private BoardUtils() {
        throw new RuntimeException("You cannot instance me");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);

        return column;
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
