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
public class Rook extends Piece{
    
    public Rook(Colour colour) {
        super(PieceType.Rook, colour, 5);
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
        return threatened;
    }

    @Override
    public boolean[][] validMoves(Board board, int indexX, int indexY) {
        return null;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "wR" : "bR";
    }
    
}
