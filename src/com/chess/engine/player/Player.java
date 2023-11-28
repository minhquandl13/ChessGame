package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = etablishKing();

        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastle(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public Piece getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    static Collection<Move> calculateAttackOnTile(final int tile,
                                                  final Collection<Move> moves) {
        return moves.stream()
                .filter(move -> move.getDestinationCoordinate() == tile)
                .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    private King etablishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }

        throw new RuntimeException("Should not reach here! Not a valid board!!");
    }

    public boolean isMoveLegal(final Move move) {
        return /*move != null && move.getMovedPiece() != null &&*/ this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && hasEscapeMoves();
    }

    public boolean isCastle() {
        return false;
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastCapable();
    }

    protected boolean hasEscapeMoves() {
        return this.legalMoves.stream()
                .noneMatch(move -> makeMove(move)
                        .getMoveStatus().isDone());
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks =
                Player.calculateAttackOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                        transitionBoard.currentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastle(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}
