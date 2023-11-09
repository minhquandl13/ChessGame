package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.*;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = etablishKing();
        this.legalMoves = legalMoves;
    }

    private King etablishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }

        throw new RuntimeException("Should not reach here! Not a valid board!!");
    }

    public void isMoveLegal(final Move move) {
        this.legalMoves.contains(move);
    }

    // TODO (quan): implements these method below!!!
    public boolean isInCheck() {
        return false;
    }

    public boolean isCheckMate() {
        return false;
    }

    public boolean isInStaleMate() {
        return false;
    }

    public boolean isCastle() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
