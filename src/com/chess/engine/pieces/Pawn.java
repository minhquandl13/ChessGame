package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.*;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        boolean isSecondRow = BoardUtils.SECOND_ROW[this.piecePosition];
        boolean isSeventhRow = BoardUtils.SEVENTH_ROW[this.piecePosition];
        boolean isFirstColumn = BoardUtils.FIRST_COLUMN[this.piecePosition];
        boolean isEightColumn = BoardUtils.EIGHT_COLUMN[this.piecePosition];


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
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

                case 16 -> {
                    if (this.isFirstMove()
                            && (isSecondRow && this.getPieceAlliance().isBlack())
                            || (isSeventhRow && this.getPieceAlliance().isWhite())) {

                        final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);

                        if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                                && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }
                }

                case 7 -> {
                    if (!(isEightColumn && this.pieceAlliance.isWhite()
                            || isFirstColumn && this.pieceAlliance.isBlack())) {
                        if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                            if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                // TODO (quan): more to do here's the case attacking into upon promotion
                                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                            }
                        }
                    }
                }

                case 9 -> {
                    boolean isBlack = this.pieceAlliance.isBlack();
                    boolean isWhite = this.pieceAlliance.isWhite();

                    if ((isFirstColumn && isWhite) || (isEightColumn && isBlack)) {
                        if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                            if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                // TODO(quan): more to do here's the case attacking into upon promotion
                                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
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
