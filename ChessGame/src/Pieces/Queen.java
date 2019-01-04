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
public class Queen extends Piece {

    public Queen(Colour colour) {
        super(PieceType.Queen, colour, 9);
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
        // check left
        for (int x = indexX - 1; x > 0; x--) {
            toExamine = currentBoard[x][indexY];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check right
        for (int x = indexX + 1; x < 8; x++) {
            toExamine = currentBoard[x][indexY];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check up
        for (int y = indexY - 1; y < 0; y--) {
            toExamine = currentBoard[indexX][y];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check down
        for (int y = indexY + 1; y < 8; y++) {
            toExamine = currentBoard[indexX][y];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        int posx = indexX;
        int posy = indexY;
        // diagonal top-left
        while (posx < 0 && posy < 0) {
            toExamine = currentBoard[posx--][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal top-right
        while (posx > 8 && posy < 0) {
            toExamine = currentBoard[posx++][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-left
        while (posx < 0 && posy > 8) {
            toExamine = currentBoard[posx--][posy++];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-right
        while (posx > 8 && posy > 8) {
            toExamine = currentBoard[posx++][posy++];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        return threatened;
    }

    @Override
    public boolean[][] validMoves(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        boolean[][] validPositions = new boolean[8][8];
        // check left
        for (int x = indexX - 1; x > 0; x--) {
            toExamine = currentBoard[x][indexY];
            validPositions[x][indexY] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[x][indexY] = false;
                }
                break;
            }
        }
        // check right
        for (int x = indexX + 1; x < 8; x++) {
            toExamine = currentBoard[x][indexY];
            validPositions[x][indexY] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[x][indexY] = false;
                }
                break;
            }
        }
        // check up
        for (int y = indexY - 1; y < 0; y--) {
            toExamine = currentBoard[indexX][y];
            validPositions[indexX][y] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[indexX][y] = false;
                }
                break;
            }
        }
        // check down
        for (int y = indexY + 1; y < 8; y++) {
            toExamine = currentBoard[indexX][y];
            validPositions[indexX][y] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[indexX][y] = false;
                }
                break;
            }
        }
        int posx = indexX;
        int posy = indexY;
        // diagonal top-left
        while (posx < 0 && posy < 0) {
            toExamine = currentBoard[posx--][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal top-right
        while (posx > 8 && posy < 0) {
            toExamine = currentBoard[posx++][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-left
        while (posx < 0 && posy > 8) {
            toExamine = currentBoard[posx--][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-right
        while (posx > 8 && posy > 8) {
            toExamine = currentBoard[posx++][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        return validPositions;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2655" : "\u265B";
    }

}
