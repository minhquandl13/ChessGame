package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {
    private static final int CHECK_BONUS = 50;
    private static final int CHECK_MATE_BONUS = 10_000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;

    @Override
    public int evaluate(final Board board, final int depth) {
        // get the score from white and subtract it from the score from black
        // if white has an advantage score that get back will be a positive number(+)
        // if black has an advantage score that get back will be a negative number(-)

        int whitePoints = calculateTotalPoints(board.whitePlayer());
        int blackPoints = calculateTotalPoints(board.blackPlayer());

        int evaluation = scorePlayer(board.whitePlayer(), whitePoints, depth)
                - scorePlayer(board.blackPlayer(), blackPoints, depth);

        // Print the result just once
        if (depth == 0) {
            System.out.println("WHITE - Total Points: " + whitePoints);
            System.out.println("WHITE - Total Number of Pieces: " + board.whitePlayer().getActivePieces().size());
            System.out.println("-----");
            System.out.println("BLACK - Total Points: " + blackPoints);
            System.out.println("BLACK - Total Number of Pieces: " + board.blackPlayer().getActivePieces().size());
        }

        return evaluation;
    }

    private int scorePlayer(final Player player, int totalPoints, final int depth) {
        return totalPoints
                + pieceValue(player)
                + mobility(player)
                + check(player)
                + checkMate(player, depth)
                + castled(player);
        // + checkmate, check, castled, mobility,....
    }

    private static int castled(final Player player) {
        return player.isCastle() ? CASTLE_BONUS : 0;
    }

    private static int checkMate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    // how many option(legals move) that player have in this position
    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int pieceValue(Player player) {
        int pieceValueScore = 0;

        for (final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }

        return pieceValueScore;
    }

    private static int calculateTotalPoints(Player player) {
        int totalPoints = 0;

        for (final Piece piece : player.getActivePieces()) {
            totalPoints += piece.getPieceValue();
        }

        return totalPoints;
    }
}
