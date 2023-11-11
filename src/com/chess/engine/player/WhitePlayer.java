package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.*;

import static com.chess.engine.board.Move.*;

public class WhitePlayer extends Player {
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
                                                   final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // white king side
            if (!this.board.getTile(61).isTileOccupied()
                    && !this.board.getTile(62).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(63);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttackOnTile(61, opponentLegals).isEmpty()
                            && Player.calculateAttackOnTile(62, opponentLegals).isEmpty()
                            && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }

            // white queen side
            if (!this.board.getTile(59).isTileOccupied()
                    && !this.board.getTile(58).isTileOccupied()
                    && !this.board.getTile(57).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(56);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttackOnTile(59, opponentLegals).isEmpty()
                            && Player.calculateAttackOnTile(58, opponentLegals).isEmpty()
                            && Player.calculateAttackOnTile(57, opponentLegals).isEmpty()
                            && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, // king position when castle
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59)); // rook position when castle
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
