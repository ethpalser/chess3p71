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
    public int[][] attacks(Board board, int indexX, int indexY) {
        int[][] attacked = new int[8][8];
        if (colour == Colour.White && indexY >= 1) {
            // check if there are pieces that can be taken
            if (indexX >= 1) {
                attacked[indexX - 1][indexY - 1]++;

            }
            if (indexX <= 6) {
                attacked[indexX + 1][indexY - 1]++;

            }
        } else if (colour == Colour.Black && indexY <= 6) {
            // check if there are pieces that can be taken
            if (indexX >= 1) {
                attacked[indexX - 1][indexY + 1]++;

            }
            if (indexX <= 6) {
                attacked[indexX + 1][indexY + 1]++;

            }
        }
        return attacked;
    }

    @Override
    public boolean[][] validMoves(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        boolean[][] validPositions = new boolean[8][8];
        if (colour == Colour.White && indexY >= 1) {
            // check if there are pieces that can be taken
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[indexX - 1][indexY - 1] = true;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[indexX + 1][indexY - 1] = true;
                }
            }
            // check if it can move up 2
            if (indexY == 6) {
                toExamine = currentBoard[indexX][indexY - 2];
                validPositions[indexX][indexY - 2] = true;
                if (toExamine != null) {
                    validPositions[indexX][indexY - 2] = false;
                }
            }
            // check if it can move up
            toExamine = currentBoard[indexX][indexY - 1];
            validPositions[indexX][indexY - 1] = true;
            if (toExamine != null) {
                validPositions[indexX][indexY - 1] = false;
            }
        } else if (colour == Colour.Black && indexY <= 6) {
            // check if there are pieces that can be taken
            if (indexX >= 1) {
                toExamine = currentBoard[indexX - 1][indexY + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[indexX - 1][indexY + 1] = true;
                }
            }
            if (indexX <= 6) {
                toExamine = currentBoard[indexX + 1][indexY + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[indexX + 1][indexY + 1] = true;
                }
            }
            // check if it can move up 2
            if (indexY == 1) {
                toExamine = currentBoard[indexX][indexY + 2];
                validPositions[indexX][indexY + 2] = true;
                if (toExamine != null) {
                    validPositions[indexX][indexY + 2] = false;
                }
            }
            // check if it can move up
            toExamine = currentBoard[indexX][indexY + 1];
            validPositions[indexX][indexY + 1] = true;
            if (toExamine != null) {
                validPositions[indexX][indexY + 1] = false;
            }
        }
        return validPositions;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2659" : "\u265F";
    }

    public boolean canEnPassant(Pawn pawn) {
        return false; // need to check if target pawn has moved 2 last turn
    }

}
