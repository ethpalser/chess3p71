package Pieces;

import Game.Board;
import Game.Colour;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author E
 */
public class Pawn extends Piece {

    public Pawn(Colour colour) {
        super(PieceType.Pawn, colour, 1);
    }

    @Override
    public int heuristic(Board board, int indexX, int indexY) {
        return threats(board, indexX, indexY)
                + this.isThreatened(board, indexX, indexY);
    }

    @Override
    public int threats(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        if (colour == Colour.White && indexY >= 1) {
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        } else if (colour == Colour.Black && indexY <= 6) {
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        return threatened;
    }

    @Override
    public boolean[][] validMoves(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        boolean[][] validPositions = new boolean[8][8];
        if (colour == Colour.White && indexY >= 1) {
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY - 1];
                validPositions[indexX - 1][indexY - 1] = true;
                if (toExamine != null && !isOppositeColour(toExamine)) {
                    validPositions[indexX - 1][indexY - 1] = false;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY - 1];
                validPositions[indexX + 1][indexY - 1] = true;
                if (toExamine != null && !isOppositeColour(toExamine)) {
                    validPositions[indexX + 1][indexY - 1] = false;
                }
            }
        } else if (colour == Colour.Black && indexY <= 6) {
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY + 1];
                validPositions[indexX - 1][indexY + 1] = true;
                if (toExamine != null && !isOppositeColour(toExamine)) {
                    validPositions[indexX - 1][indexY + 1] = false;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY + 1];
                validPositions[indexX + 1][indexY + 1] = true;
                if (toExamine != null && !isOppositeColour(toExamine)) {
                    validPositions[indexX + 1][indexY + 1] = false;
                }
            }
        }
        return validPositions;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "wP" : "bP";
    }

    public boolean canEnPassant(Pawn pawn) {
        return false; // need to check if target pawn has moved 2 last turn
    }

}
