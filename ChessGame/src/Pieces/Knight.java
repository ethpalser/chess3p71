/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Game.Board;
import Game.Colour;

/**
 *
 * @author Param
 */
public class Knight extends Piece {

    public Knight(Colour colour) {
        super(PieceType.Knight, colour, 3);
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
        // top-left
        if (indexX >= 2 && indexY >= 1) {
            toExamine = currentBoard[indexX - 2][indexY - 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        if (indexX >= 1 && indexY >= 2) {
            toExamine = currentBoard[indexX - 1][indexY - 2];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        // top-right
        if (indexX >= 2 && indexY <= 6) {
            toExamine = currentBoard[indexX - 2][indexY + 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        if (indexX >= 1 && indexY <= 5) {
            toExamine = currentBoard[indexX - 1][indexY + 2];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        // bottom-left
        if (indexX <= 5 && indexY >= 1) {
            toExamine = currentBoard[indexX + 2][indexY - 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        if (indexX <= 6 && indexY >= 2) {
            toExamine = currentBoard[indexX + 1][indexY - 2];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        // bottom-right
        if (indexX <= 5 && indexY <= 6) {
            toExamine = currentBoard[indexX + 2][indexY + 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        if (indexX <= 6 && indexY <= 5) {
            toExamine = currentBoard[indexX + 1][indexY + 2];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
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
        // top-left
        if (indexX >= 2 && indexY >= 1) {
            toExamine = currentBoard[indexX - 2][indexY - 1];
            validPositions[indexX - 2][indexY - 1] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX - 2][indexY - 1] = false;
            }
        }
        if (indexX >= 1 && indexY >= 2) {
            toExamine = currentBoard[indexX - 1][indexY - 2];
            validPositions[indexX - 1][indexY - 2] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX - 1][indexY - 2] = false;
            }
        }
        // top-right
        if (indexX >= 2 && indexY <= 6) {
            toExamine = currentBoard[indexX - 2][indexY + 1];
            validPositions[indexX - 2][indexY + 1] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX - 2][indexY + 1] = false;
            }
        }
        if (indexX >= 1 && indexY <= 5) {
            toExamine = currentBoard[indexX - 1][indexY + 2];
            validPositions[indexX - 1][indexY + 2] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX - 1][indexY + 2] = false;
            }
        }
        // bottom-left
        if (indexX <= 5 && indexY >= 1) {
            toExamine = currentBoard[indexX + 2][indexY - 1];
            validPositions[indexX + 2][indexY - 1] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX + 2][indexY - 1] = false;
            }
        }
        if (indexX <= 6 && indexY >= 2) {
            toExamine = currentBoard[indexX + 1][indexY - 2];
            validPositions[indexX + 1][indexY - 2] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX + 1][indexY - 2] = false;
            }
        }
        // bottom-right
        if (indexX <= 5 && indexY <= 6) {
            toExamine = currentBoard[indexX + 2][indexY + 1];
            validPositions[indexX + 2][indexY + 1] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX + 2][indexY + 1] = false;
            }
        }
        if (indexX <= 6 && indexY <= 5) {
            toExamine = currentBoard[indexX + 1][indexY + 2];
            validPositions[indexX + 1][indexY + 2] = true;
            if (toExamine != null && !this.isOppositeColour(toExamine)) {
                validPositions[indexX + 1][indexY + 2] = false;
            }
        }
        return validPositions;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2658" : "\u265E";
    }
    
    public String printToLog(){
        return "N";
    }

}
