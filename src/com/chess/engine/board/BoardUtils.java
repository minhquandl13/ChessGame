package com.chess.engine.board;

import static com.chess.engine.board.Tile.NUM_TILE;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0); // the first column in board is true, and all the remain is false
    public static final boolean[] SECOND_COLUMN = initColumn(1); // the second column in board is true, and all the remain is false
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);
    public static final int NUM_TILE_PER_ROW = 0;
    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;



    private BoardUtils() {
        throw new RuntimeException("You cannot instance me");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILE_PER_ROW;
        } while (columnNumber < Tile.NUM_TILE);

        return column;
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < Tile.NUM_TILE;
    }
}
