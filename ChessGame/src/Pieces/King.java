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
public class King extends Piece {

    public King(Colour colour) {
        super(PieceType.King, colour, Integer.MAX_VALUE);
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
        if (indexY >= 1) {
            // top
            toExamine = currentBoard[indexX][indexY - 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (indexX >= 1) {
                // top-left
                toExamine = currentBoard[indexX - 1][indexY - 1];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                }
            }
            if (indexX <= 6) {
                // top-right
                toExamine = currentBoard[indexX + 1][indexY - 1];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                }
            }
        }
        if (indexY <= 6) {
            // bottom
            toExamine = currentBoard[indexX][indexY + 1];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (indexX >= 1) {
                // bottom-left
                toExamine = currentBoard[indexX - 1][indexY + 1];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                }
            }
            if (indexX <= 6) {
                // bottom-right
                toExamine = currentBoard[indexX + 1][indexY + 1];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                }
            }
        }
        if (indexX >= 1) {
            // right
            toExamine = currentBoard[indexX - 1][indexY];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        if (indexY <= 6) {// left
            toExamine = currentBoard[indexX + 1][indexY];
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
        int nextX, nextY;
        if (indexY >= 1) {
            // top
            nextX = indexX;
            nextY = indexY - 1;
            toExamine = currentBoard[nextX][nextY];
            validPositions[nextX][nextY] = true;
            if ((toExamine != null && !this.isOppositeColour(toExamine))
                    || this.isThreatened(board, nextX, nextY) > 0) {
                validPositions[nextX][nextY] = false;
            }
            if (indexX >= 1) {
                // top-left
                nextX = indexX - 1;
                nextY = indexY - 1;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
            if (indexX <= 6) {
                // top-right
                nextX = indexX + 1;
                nextY = indexY - 1;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
        }
        if (indexY <= 6) {
            // bottom
            nextX = indexX;
            nextY = indexY + 1;
            toExamine = currentBoard[nextX][nextY];
            validPositions[nextX][nextY] = true;
            if ((toExamine != null && !this.isOppositeColour(toExamine))
                    || this.isThreatened(board, nextX, nextY) > 0) {
                validPositions[nextX][nextY] = false;
            }
            if (indexX >= 1) {
                // bottom-left
                nextX = indexX - 1;
                nextY = indexY + 1;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
            if (indexX <= 6) {
                // bottom-right
                nextX = indexX + 1;
                nextY = indexY + 1;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
            if (indexX >= 1) {
                // right
                nextX = indexX + 1;
                nextY = indexY;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
            if (indexY <= 6) {// left
                nextX = indexX - 1;
                nextY = indexY;
                toExamine = currentBoard[nextX][nextY];
                validPositions[nextX][nextY] = true;
                if ((toExamine != null && !this.isOppositeColour(toExamine))
                        || this.isThreatened(board, nextX, nextY) > 0) {
                    validPositions[nextX][nextY] = false;
                }
            }
        }
        return validPositions;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2654" : "\u265A";
    }

    public boolean castle(Rook rook) {
        return false; // need to check if rook has moved
    }
}
