package com.chess.pgn;

import java.util.List;

public class Game implements Playable {
    protected final PGNGameTags tags;
    protected final List<String> moves;
    protected final String winner;

    public Game(final PGNGameTags tags,
                final List<String> moves,
                final String winner) {
        this.tags = tags;
        this.moves = moves;
        this.winner = winner;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public List<String> getMoves() {
        return this.moves;
    }

    public String getWinner() {
        return this.winner;
    }

    private static String calculateWinner(final String gameOutcome) {
        if(gameOutcome.equals("1-0")) {
            return "White";
        }
        if(gameOutcome.equals("0-1")) {
            return "Black";
        }
        if(gameOutcome.equals("1/2-1/2")) {
            return "Tie";
        }
        return "None";
    }
}
