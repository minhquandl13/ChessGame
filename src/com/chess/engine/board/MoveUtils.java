package com.chess.engine.board;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.board.Move.*;

public enum MoveUtils {

    INSTANCE;

    public static final Move NULL_MOVE = new NullMove();

    public static class Line {
        private final List<Integer> coordinates;

        public Line() {
            this.coordinates = new ArrayList<>();
        }

        public void addCoordinate(int coordinate) {
            this.coordinates.add(coordinate);
        }

        public List<Integer> getLineCoordinates() {
            return this.coordinates;
        }

        public boolean isEmpty() {
            return this.coordinates.isEmpty();
        }
    }
}
