package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.*;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance,
                final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, true);
    }

    public Pawn(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        boolean isSecondRank = BoardUtils.SEVENTH_RANK[this.piecePosition];
        boolean isSeventhRank = BoardUtils.SECOND_RANK[this.piecePosition];
        boolean isFirstColumn = BoardUtils.FIRST_COLUMN[this.piecePosition];
        boolean isEightColumn = BoardUtils.EIGHT_COLUMN[this.piecePosition];
        boolean isBlack = this.getPieceAlliance().isBlack();
        boolean isWhite = this.getPieceAlliance().isWhite();


        // BLACK apply -8, WHITE apply 8
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            switch (currentCandidateOffset) {
                case 8 -> {
                    if (!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                        // TODO: more work to do here (deal with promotion)!!
                        legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                    }
                }

                case 16 -> {
                    if (this.isFirstMove()
                            && (isSecondRank && isBlack)
                            || (isSeventhRank && isWhite)) {

                        final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);

                        if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                                && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                        }
                    }
                }

                case 7 -> {
                    if (!(isEightColumn && isWhite
                            || isFirstColumn && isBlack)) {
                        if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                            if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                // TODO (quan): more to do here's the case attacking into upon promotion
                                legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                            }
                        } else if (board.getEnPassantPawn() != null) { // if enemy pawn next to your pawn then you can take
                            if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                                final Piece pieceOnCandidate = board.getEnPassantPawn();

                                if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                    legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                                }
                            }
                        }
                    }
                }

                case 9 -> {
                    if (!(isFirstColumn && isWhite || (isEightColumn && isBlack))) {
                        if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                            if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                // TODO(quan): more to do here's the case attacking into upon promotion
                                legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                            }
                        } else if (board.getEnPassantPawn() != null) { // if enemy pawn next to your pawn then you can take
                            if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                                final Piece pieceOnCandidate = board.getEnPassantPawn();

                                if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                    legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                                }
                            }
                        }
                    }
                }
            }

//            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//                //TODO (quan): more work to do here(deal with promotion)!!
//                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
//            } else if (currentCandidateOffset == 16 && this.isFirstMove()
//                    && (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack())
//                    || BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) {
//                final int behindCandidateDestinationCoordinate = this.piecePosition
//                        + (this.pieceAlliance.getDirection() * 8);
//
//                if (board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
//                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
//                }
//            } else if (currentCandidateOffset == 7
//                    && !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()
//                    || BoardUtils.FIRST_COLUMN[this.piecePosition]) && this.pieceAlliance.isBlack())) {
//                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
//
//                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
//                        //TODO (quan): more to do here's the case attacking into upon promotion
//                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
//                    }
//                }
//            } else if (currentCandidateOffset == 9
//                    && !((BoardUtils.FIRST_COLUMN[this.piecePosition]) && this.pieceAlliance.isWhite()
//                    || BoardUtils.EIGHT_COLUMN[this.piecePosition]) && this.pieceAlliance.isBlack()) {
//                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
//
//                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
//                        //TODO (quan): more to do here's the case attacking into upon promotion
//                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
//                    }
//                }
//            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
