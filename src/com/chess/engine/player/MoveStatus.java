package com.chess.engine.player;

public enum MoveStatus {
    // Can move
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },

    // Cannot move
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    },

    // Cannot move
    LEAVES_PLAYER_IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();
}
